package com.mySportPage.dataAcquisition.dao;

import com.mySportPage.dataAcquisition.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DataAcquisitionDao {

    private static final Logger log = LoggerFactory.getLogger(DataAcquisitionDao.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Map<SportObjectEnum, List<String>> queriesForChecking = new HashMap<>() {{
        put(SportObjectEnum.TEAM, List.of("SELECT EXISTS (SELECT 1 FROM public.team WHERE team_id = :internalId)"));
        put(SportObjectEnum.STADIUM, List.of("SELECT EXISTS (SELECT 1 FROM public.stadium WHERE stadium_id = :internalId)",
                "SELECT EXISTS (SELECT 1 FROM public.stadium WHERE stadium_id = :internalId AND :externalId = ANY(team_id))"));
        put(SportObjectEnum.LEAGUE, List.of("SELECT EXISTS (SELECT 1 FROM public.league WHERE league_id = :internalId AND country = CAST(:externalId AS text))"));
        put(SportObjectEnum.LEAGUE_COVERAGE, List.of("SELECT EXISTS (SELECT 1 FROM public.league_coverage WHERE external_league_id = :internalId)"));
        put(SportObjectEnum.COUNTRY, List.of("SELECT EXISTS (SELECT 1 FROM public.country WHERE name = :internalId)"));
        put(SportObjectEnum.FIXTURE, List.of("SELECT EXISTS (SELECT 1 FROM public.fixture WHERE fixture_id = :internalId)"));
    }};

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void persistTeams(List<Team> teams) {
        if (teams.isEmpty()) {
            return;
        }
        String persistTeam = "INSERT INTO public.team (team_id, name, shortcut, club_crest, club_founded, country) " +
                "VALUES(:externalTeamId, :name, :shortcut, :clubCrest, :clubFounded, :country);";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Team team : teams) {
            parameters.addValue("externalTeamId", team.getExternalTeamId());
            parameters.addValue("name", team.getName());
            parameters.addValue("shortcut", team.getShortCut());
            parameters.addValue("clubCrest", team.getClubCrest());
            parameters.addValue("clubFounded", team.getClubFounded());
            parameters.addValue("country", team.getCountry());

            if (!checkIfObjectAlreadyExist(SportObjectEnum.TEAM, team.getExternalTeamId(), null)) {
                this.namedParameterJdbcTemplate.update(persistTeam, parameters);
                log.info("Team: {} stored in db.", team.getName());
            }
        }
    }

    public void persistStadiums(List<Stadium> stadiums) {
        if (stadiums.isEmpty()) {
            return;
        }
        String persistStadium = "INSERT INTO public.stadium (stadium_id, stadium, team_id, capacity, address, city) " +
                "VALUES(:id, :stadium, ARRAY [:teamId], :capacity, :address, :city);";

        String updateStadium = "UPDATE public.stadium SET team_id = array_append(team_id, :teamId) WHERE stadium_id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Stadium stadium : stadiums) {
            parameters.addValue("id", stadium.getId());
            parameters.addValue("stadium", stadium.getStadium());
            parameters.addValue("teamId", stadium.getExternalTeamId());
            parameters.addValue("capacity", stadium.getCapacity());
            parameters.addValue("address", stadium.getAddress());
            parameters.addValue("city", stadium.getCity());

            if (!checkIfObjectAlreadyExist(SportObjectEnum.STADIUM, stadium.getId(), null)) {
                this.namedParameterJdbcTemplate.update(persistStadium, parameters);
                log.info("Stadium: {} stored in db.", stadium.getStadium());
            } else if (!checkIfObjectAlreadyExist(SportObjectEnum.STADIUM, stadium.getId(), stadium.getExternalTeamId())) {
                this.namedParameterJdbcTemplate.update(updateStadium, parameters);
                log.info("Stadium: {} now has additional team assigned to with id: {}", stadium.getStadium(), stadium.getExternalTeamId());
            }
        }
    }

    public void persistLeague(List<League> leagues) {
        if (leagues.isEmpty()) {
            return;
        }

        String persistLeague = "INSERT INTO public.league (league_id, name, type, logo, year, start, \"end\", country) " +
                "VALUES(:leagueId, :name, :type, :logo, :year, :start, :end, :country)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (League league : leagues) {
            parameters.addValue("leagueId", league.getExternalLeagueId());
            parameters.addValue("name", league.getName());
            parameters.addValue("type", league.getType());
            parameters.addValue("logo", league.getLogo());
            parameters.addValue("year", league.getYear());
            parameters.addValue("start", league.getStart());
            parameters.addValue("end", league.getEnd());
            parameters.addValue("country", league.getCountry().getName());

            if (!checkIfObjectAlreadyExist(SportObjectEnum.LEAGUE, league.getExternalLeagueId(), league.getCountry().getName())) {
                this.namedParameterJdbcTemplate.update(persistLeague, parameters);
                log.info("League: {} stored in db.", league.getName());
            }
        }
    }

    public void persistLeagueCoverage(List<LeagueCoverage> leagueCoverages) {
        if (leagueCoverages.isEmpty()) {
            return;
        }

        String persistCoverage = "INSERT INTO public.league_coverage " +
                "(external_league_id, events, lineups, statistics_fixtures, statistics_players, standings, players, top_scorers, top_assists, top_cards, injuries, predictions, odds) " +
                "VALUES(:externalLeagueId, :events, :lineups, :statisticsFixtures, :statisticsPlayers, :standings, :players, :topScorers, :topAssists, :topCards, :injuries, :predictions, :odds)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (LeagueCoverage leagueCoverage : leagueCoverages) {
            parameters.addValue("externalLeagueId", leagueCoverage.getExternalLeagueId());
            parameters.addValue("events", leagueCoverage.isWithEvents());
            parameters.addValue("lineups", leagueCoverage.isWithLineups());
            parameters.addValue("statisticsFixtures", leagueCoverage.isWithStatisticsFixtures());
            parameters.addValue("statisticsPlayers", leagueCoverage.isWithStatisticsPlayers());
            parameters.addValue("standings", leagueCoverage.isWithStandings());
            parameters.addValue("players", leagueCoverage.isWithPlayers());
            parameters.addValue("topScorers", leagueCoverage.isWithTopScorers());
            parameters.addValue("topAssists", leagueCoverage.isWithTopAssists());
            parameters.addValue("topCards", leagueCoverage.isWithTopCards());
            parameters.addValue("injuries", leagueCoverage.isWithInjuries());
            parameters.addValue("predictions", leagueCoverage.isWithPredictions());
            parameters.addValue("odds", leagueCoverage.isWithOdds());

            if (!checkIfObjectAlreadyExist(SportObjectEnum.LEAGUE_COVERAGE, leagueCoverage.getExternalLeagueId(), null)) {
                this.namedParameterJdbcTemplate.update(persistCoverage, parameters);
                log.info("Coverage for league with id = {} stored in db.", leagueCoverage.getExternalLeagueId());
            }
        }
    }

    public void persistCountry(List<Country> countries) {
        if (countries.isEmpty()) {
            return;
        }

        String persistCountry = "INSERT INTO public.country (\"name\", code, flag) VALUES(:name, :code, :flag)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Country country : countries) {
            parameters.addValue("name", country.getName());
            parameters.addValue("code", country.getCode());
            parameters.addValue("flag", country.getFlag());

            if (!checkIfObjectAlreadyExist(SportObjectEnum.COUNTRY, country.getName(), null)) {
                this.namedParameterJdbcTemplate.update(persistCountry, parameters);
                log.info("Country {} stored in db.", country.getName());
            }
        }
    }

    public void persistFixture(List<Fixture> fixtures) {
        if (fixtures.isEmpty()) {
            return;
        }

        String persistFixture = "INSERT INTO public.fixture " +
                "(fixture_id, \"event\", league_id, round, season, \"start\", host, guest, winner, stadium_id, referee, \"result\", halftime_score, fulltime_score, played) " +
                "VALUES(:id, :event, :leagueId, :round, :season, :start, :host, :guest, :winner, :stadiumId, :referee, :result, :halftimeScore, :fulltimeScore, :finished);";

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        for (Fixture fixture : fixtures) {
            String halftimeResult = String.format("%s:%s",
                    fixture.getHalftimeScore().get("HOST"),
                    fixture.getHalftimeScore().get("GUEST"));
            String fulltimeResult = String.format("%s:%s",
                    fixture.getFulltimeScore().get("HOST"),
                    fixture.getFulltimeScore().get("GUEST"));
            parameters.addValue("id", fixture.getId());
            parameters.addValue("event", fixture.getEvent());
            parameters.addValue("leagueId", fixture.getLeagueId());
            parameters.addValue("round", fixture.getRound());
            parameters.addValue("season", fixture.getSeason());
            parameters.addValue("start", fixture.getStart());
            parameters.addValue("host", fixture.getHost().getName());
            parameters.addValue("guest", fixture.getGuest().getName());
            parameters.addValue("winner", fixture.getWinner());
            parameters.addValue("stadiumId", fixture.getStadiumId());
            parameters.addValue("referee", fixture.getReferee().getName());
            parameters.addValue("result", String.format("%s (%s)",fulltimeResult, halftimeResult));
            parameters.addValue("halftimeScore", halftimeResult);
            parameters.addValue("fulltimeScore", fulltimeResult);
            parameters.addValue("finished", fixture.isFinished());

            if (!checkIfObjectAlreadyExist(SportObjectEnum.FIXTURE, fixture.getId(), null)) {
                this.namedParameterJdbcTemplate.update(persistFixture, parameters);
                log.info("Fixture {} stored in db.", fixture.getEvent());
            }
        }

    }


    private boolean checkIfObjectAlreadyExist(SportObjectEnum object, Object internalId, Object externalId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("internalId", internalId);
        parameters.addValue("externalId", externalId);
        int queryIndex = (externalId != null && object.equals(SportObjectEnum.STADIUM)) ? 1 : 0;
        return Boolean.TRUE.equals(
                namedParameterJdbcTemplate.queryForObject(
                        queriesForChecking.get(object).get(queryIndex),
                        parameters,
                        Boolean.class));
    }
}
