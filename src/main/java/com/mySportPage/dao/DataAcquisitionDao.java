package com.mySportPage.dao;

import com.mySportPage.model.*;
import com.mySportPage.model.Fixture;
import com.mySportPage.model.League;
import com.mySportPage.model.Team;
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
        put(SportObjectEnum.TEAM, List.of("SELECT EXISTS (SELECT 1 FROM football.team WHERE team_id = :internalId)"));
        put(SportObjectEnum.STADIUM, List.of("SELECT EXISTS (SELECT 1 FROM football.stadium WHERE stadium_id = :internalId)",
                "SELECT EXISTS (SELECT 1 FROM football.stadium WHERE stadium_id = :internalId AND :externalId = ANY(team_id))"));
        put(SportObjectEnum.LEAGUE, List.of("SELECT EXISTS (SELECT 1 FROM football.league WHERE league_id = :internalId AND country = CAST(:externalId AS text))"));
        put(SportObjectEnum.LEAGUE_COVERAGE, List.of("SELECT EXISTS (SELECT 1 FROM football.league_coverage WHERE external_league_id = :internalId)"));
        put(SportObjectEnum.COUNTRY, List.of("SELECT EXISTS (SELECT 1 FROM football.country WHERE name = :internalId)"));
        put(SportObjectEnum.FIXTURE, List.of("SELECT EXISTS (SELECT 1 FROM football.fixture WHERE fixture_id = :internalId)"));
        put(SportObjectEnum.RESULTS, List.of("SELECT EXISTS (SELECT 1 FROM football.results WHERE description = :internalId AND team_id = :externalId)"));
        put(SportObjectEnum.STANDING, List.of("SELECT EXISTS (SELECT 1 FROM football.standing WHERE updated = :internalId AND team = :externalId)"));
    }};

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void persistTeams(List<Team> teams) {
        if (teams.isEmpty()) {
            return;
        }
        String queryPersistTeam = "INSERT INTO football.team (team_id, name, shortcut, club_crest, club_founded, country) " +
                "VALUES(:externalTeamId, :name, :shortcut, :clubCrest, :clubFounded, :country)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Team team : teams) {
            parameters.addValue("externalTeamId", team.getExternalTeamId());
            if (!checkIfObjectAlreadyExist(SportObjectEnum.TEAM, team.getExternalTeamId(), null)) {
                parameters.addValue("name", team.getName());
                parameters.addValue("shortcut", team.getShortCut());
                parameters.addValue("clubCrest", team.getClubCrest());
                parameters.addValue("clubFounded", team.getClubFounded());
                parameters.addValue("country", team.getCountry());

                this.namedParameterJdbcTemplate.update(queryPersistTeam, parameters);
                log.info("Team: {} stored in db.", team.getName());
            }
        }
    }

    public void persistStadiums(List<Stadium> stadiums) {
        if (stadiums.isEmpty()) {
            return;
        }
        String queryPersistStadium = "INSERT INTO football.stadium (stadium_id, stadium, team_id, capacity, address, city) " +
                "VALUES(:id, :stadium, ARRAY [:teamId], :capacity, :address, :city)";

        String queryUpdateStadium = "UPDATE public.stadium SET team_id = array_append(team_id, :teamId) WHERE stadium_id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Stadium stadium : stadiums) {
            parameters.addValue("id", stadium.getId());
            parameters.addValue("teamId", stadium.getExternalTeamId());
            if (!checkIfObjectAlreadyExist(SportObjectEnum.STADIUM, stadium.getId(), null)) {
                parameters.addValue("stadium", stadium.getStadium());
                parameters.addValue("capacity", stadium.getCapacity());
                parameters.addValue("address", stadium.getAddress());
                parameters.addValue("city", stadium.getCity());

                this.namedParameterJdbcTemplate.update(queryPersistStadium, parameters);
                log.info("Stadium: {} stored in db.", stadium.getStadium());
            } else if (!checkIfObjectAlreadyExist(SportObjectEnum.STADIUM, stadium.getId(), stadium.getExternalTeamId())) {
                this.namedParameterJdbcTemplate.update(queryUpdateStadium, parameters);
                log.info("Stadium: {} now has additional team assigned to with id: {}", stadium.getStadium(), stadium.getExternalTeamId());
            }
        }
    }

    public void persistLeague(List<League> leagues) {
        if (leagues.isEmpty()) {
            return;
        }

        String queryPersistLeague = "INSERT INTO football.league (league_id, name, type, logo, year, start, \"end\", country) " +
                "VALUES(:leagueId, :name, :type, :logo, :year, :start, :end, :country)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (League league : leagues) {
            parameters.addValue("leagueId", league.getExternalLeagueId());
            parameters.addValue("name", league.getName());
            if (!checkIfObjectAlreadyExist(SportObjectEnum.LEAGUE, league.getExternalLeagueId(), league.getCountry().getName())) {
                parameters.addValue("type", league.getType());
                parameters.addValue("logo", league.getLogo());
                parameters.addValue("year", league.getYear());
                parameters.addValue("start", league.getStart());
                parameters.addValue("end", league.getEnd());
                parameters.addValue("country", league.getCountry().getName());

                this.namedParameterJdbcTemplate.update(queryPersistLeague, parameters);
                log.info("League: {} stored in db.", league.getName());
            }
        }
    }

    public void persistLeagueCoverage(List<LeagueCoverage> leagueCoverages) {
        if (leagueCoverages.isEmpty()) {
            return;
        }

        String queryPersistCoverage = "INSERT INTO football.league_coverage " +
                "(external_league_id, events, lineups, statistics_fixtures, statistics_players, standings, players, top_scorers, top_assists, top_cards, injuries, predictions, odds) " +
                "VALUES(:externalLeagueId, :events, :lineups, :statisticsFixtures, :statisticsPlayers, :standings, :players, :topScorers, :topAssists, :topCards, :injuries, :predictions, :odds)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (LeagueCoverage leagueCoverage : leagueCoverages) {
            parameters.addValue("externalLeagueId", leagueCoverage.getExternalLeagueId());
            if (!checkIfObjectAlreadyExist(SportObjectEnum.LEAGUE_COVERAGE, leagueCoverage.getExternalLeagueId(), null)) {
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

                this.namedParameterJdbcTemplate.update(queryPersistCoverage, parameters);
                log.info("Coverage for league with id = {} stored in db.", leagueCoverage.getExternalLeagueId());
            }
        }
    }

    public void persistCountry(List<Country> countries) {
        if (countries.isEmpty()) {
            return;
        }

        String queryPersistCountry = "INSERT INTO football.country (\"name\", code, flag) VALUES(:name, :code, :flag)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Country country : countries) {
            parameters.addValue("name", country.getName());
            if (!checkIfObjectAlreadyExist(SportObjectEnum.COUNTRY, country.getName(), null)) {
                parameters.addValue("code", country.getCode());
                parameters.addValue("flag", country.getFlag());

                this.namedParameterJdbcTemplate.update(queryPersistCountry, parameters);
                log.info("Country {} stored in db.", country.getName());
            }
        }
    }

    public void persistFixture(List<Fixture> fixtures) {
        if (fixtures.isEmpty()) {
            return;
        }

        String queryPersistFixture = "INSERT INTO football.fixture " +
                "(fixture_id, \"event\", league_id, round, season, \"start\", host, guest, winner, stadium_id, referee, \"result\", halftime_score, fulltime_score, played) " +
                "VALUES(:id, :event, :leagueId, :round, :season, :start, :host, :guest, :winner, :stadiumId, :referee, :result, :halftimeScore, :fulltimeScore, :finished);";

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        for (Fixture fixture : fixtures) {
            parameters.addValue("id", fixture.getId());
            if (!checkIfObjectAlreadyExist(SportObjectEnum.FIXTURE, fixture.getId(), null)) {
                String halftimeResult = fixture.isFinished() ?
                        String.format("%s:%s",
                                fixture.getHalftimeScore().get("HOST"),
                                fixture.getHalftimeScore().get("GUEST")) : null;
                String fulltimeResult = fixture.isFinished() ?
                        String.format("%s:%s",
                                fixture.getFulltimeScore().get("HOST"),
                                fixture.getFulltimeScore().get("GUEST")) : null;
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
                parameters.addValue("result", fixture.isFinished() ? String.format("%s (%s)", fulltimeResult, halftimeResult) : null);
                parameters.addValue("halftimeScore", halftimeResult);
                parameters.addValue("fulltimeScore", fulltimeResult);
                parameters.addValue("finished", fixture.isFinished());

                this.namedParameterJdbcTemplate.update(queryPersistFixture, parameters);
                log.info("Fixture {} stored in db.", fixture.getEvent());
            }
        }
    }

    public void persistStanding(List<Standing> standings) {
        if (standings.isEmpty()) {
            return;
        }

        String queryPersistStandings = "INSERT INTO football.standing " +
                "(\"rank\", team, points, goals_diff, form, league_id, season, position_description, actual_results_id, home_results_id, away_results_id, updated) " +
                "VALUES(:rank, :team, :points, :goalsDiff, :form, :leagueId, :season, :additionalPositionDescription, :resultsId, :homeResultsId, :awayResultsId, :updated)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Standing standing : standings) {
            parameters.addValue("updated", standing.getUpdated());
            parameters.addValue("team", standing.getTeam().getName());
            if (!checkIfObjectAlreadyExist(SportObjectEnum.STANDING, standing.getUpdated(), standing.getTeam().getName())) {
                parameters.addValue("resultsId", prepareResults(parameters, standing.getResults()));
                parameters.addValue("homeResultsId", prepareResults(parameters, standing.getHomeResults()));
                parameters.addValue("awayResultsId", prepareResults(parameters, standing.getAwayResults()));
                parameters.addValue("rank", standing.getRank());
                parameters.addValue("points", standing.getPoints());
                parameters.addValue("goalsDiff", standing.getGoalsDiff());
                parameters.addValue("form", standing.getForm());
                parameters.addValue("leagueId", standing.getLeague().getExternalLeagueId());
                parameters.addValue("season", standing.getSeason());
                parameters.addValue("additionalPositionDescription", standing.getAdditionalPositionDescription());

                this.namedParameterJdbcTemplate.update(queryPersistStandings, parameters);
                log.info("{}s standing updated in db.", standing.getTeam().getName());
            }
        }
    }

    private Integer prepareResults(MapSqlParameterSource parameters, Results results) {

        String queryPersistResults = "INSERT INTO football.results (team_id, rounds_played, wins, draws, loses, goals_for, goals_against, description) " +
                "VALUES(:teamId, :roundsPlayed, :wins, :draws, :loses, :goalsFor, :goalsAgainst, :description) " +
                "RETURNING id";

        String queryGetResultId = "SELECT id FROM public.results WHERE description = :description AND team_id = :teamId";

        parameters.addValue("description", results.getDescription());
        parameters.addValue("teamId", results.getTeam().getExternalTeamId());
        if (!checkIfObjectAlreadyExist(SportObjectEnum.RESULTS, results.getDescription(), results.getTeam().getExternalTeamId())) {
            parameters.addValue("roundsPlayed", results.getRoundsPlayed());
            parameters.addValue("wins", results.getWins());
            parameters.addValue("draws", results.getDraws());
            parameters.addValue("loses", results.getLoses());
            parameters.addValue("goalsFor", results.getGoals().getOrDefault("FOR", 0));
            parameters.addValue("goalsAgainst", results.getGoals().getOrDefault("AGAINST", 0));

            log.info("{}s results stored in db.", results.getTeam().getName());
            return this.namedParameterJdbcTemplate.queryForObject(queryPersistResults, parameters, Integer.class);
        } else {
            log.info("{}s results updated in db.", results.getTeam().getName());
            return this.namedParameterJdbcTemplate.queryForObject(queryGetResultId, parameters, Integer.class);
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
