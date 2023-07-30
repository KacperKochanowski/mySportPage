package com.mySportPage.dao;

import com.mySportPage.model.*;
import com.mySportPage.model.Fixture;
import com.mySportPage.model.League;
import com.mySportPage.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

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
        put(SportObjectEnum.FIXTURE_STATS, List.of("SELECT EXISTS (SELECT 1 FROM football.fixture_statistics WHERE fixture_id = :internalId AND team_id = :externalId)"));
        put(SportObjectEnum.COACH, List.of("SELECT EXISTS (SELECT 1 FROM football.coach WHERE external_id = :externalId)"));
        put(SportObjectEnum.COACH_HISTORY, List.of("SELECT EXISTS (SELECT 1 FROM football.coach_career WHERE coach_id = :coachId AND team_id = :teamId AND start = :start AND CASE WHEN \"end\" IS NOT NULL THEN \"end\" = :end ELSE TRUE END)"));
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
            if (!objectAlreadyExists(SportObjectEnum.TEAM, team.getExternalTeamId(), null)) {
                parameters.addValue("externalTeamId", team.getExternalTeamId());
                parameters.addValue("name", team.getName());
                parameters.addValue("shortcut", team.getShortCut());
                parameters.addValue("clubCrest", team.getClubCrest());
                parameters.addValue("clubFounded", team.getClubFounded());
                parameters.addValue("country", team.getCountry());

                this.namedParameterJdbcTemplate.update(queryPersistTeam, parameters);
                log.info("Team: {} stored in database.", team.getName());
                continue;
            }
            log.debug("Team {} already exists in database.", team.getName());
        }
    }

    public void persistStadiums(List<Stadium> stadiums) {
        if (stadiums.isEmpty()) {
            return;
        }
        String queryPersistStadium = "INSERT INTO football.stadium (stadium_id, stadium, team_id, capacity, address, city) " +
                "VALUES(:id, :stadium, ARRAY [:teamId], :capacity, :address, :city)";

        String queryUpdateStadium = "UPDATE football.stadium SET team_id = array_append(team_id, :teamId) WHERE stadium_id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Stadium stadium : stadiums) {
            parameters.addValue("id", stadium.getId());
            parameters.addValue("teamId", stadium.getExternalTeamId());
            if (!objectAlreadyExists(SportObjectEnum.STADIUM, stadium.getId(), null)) {
                parameters.addValue("stadium", stadium.getStadium());
                parameters.addValue("capacity", stadium.getCapacity());
                parameters.addValue("address", stadium.getAddress());
                parameters.addValue("city", stadium.getCity());

                this.namedParameterJdbcTemplate.update(queryPersistStadium, parameters);
                log.info("Stadium: {} stored in database.", stadium.getStadium());
            } else if (!objectAlreadyExists(SportObjectEnum.STADIUM, stadium.getId(), stadium.getExternalTeamId())) {
                this.namedParameterJdbcTemplate.update(queryUpdateStadium, parameters);
                log.info("Stadium: {} now has additional team assigned to with id: {}", stadium.getStadium(), stadium.getExternalTeamId());
            } else {
                log.debug("Stadium {} already exists in database.", stadium.getStadium());
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
            if (!objectAlreadyExists(SportObjectEnum.LEAGUE, league.getExternalLeagueId(), league.getCountry().getName())) {
                parameters.addValue("type", league.getType());
                parameters.addValue("logo", league.getLogo());
                parameters.addValue("year", league.getYear());
                parameters.addValue("start", league.getStart());
                parameters.addValue("end", league.getEnd());
                parameters.addValue("country", league.getCountry().getName());

                this.namedParameterJdbcTemplate.update(queryPersistLeague, parameters);
                log.info("League: {} stored in database.", league.getName());
                continue;
            }
            log.debug("League {} already exists in database.", league.getName());
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
            if (!objectAlreadyExists(SportObjectEnum.LEAGUE_COVERAGE, leagueCoverage.getExternalLeagueId(), null)) {
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
                log.info("Coverage for league with id {} stored in database.", leagueCoverage.getExternalLeagueId());
                continue;
            }
            log.debug("Coverage for league with id {} already exists in database.", leagueCoverage.getExternalLeagueId());
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
            if (!objectAlreadyExists(SportObjectEnum.COUNTRY, country.getName(), null)) {
                parameters.addValue("code", country.getCode());
                parameters.addValue("flag", country.getFlag());

                this.namedParameterJdbcTemplate.update(queryPersistCountry, parameters);
                log.info("Country {} stored in database.", country.getName());
                continue;
            }
            log.debug("Country {} already exists in database.", country.getName());
        }
    }

    @CacheEvict(value = {"allFixtures", "twoWeeksFixtures", "fixturesByTeam", "fixturesByTeamAndWeatherPlayed", "fixturesByLeagueAndAdditionallyRound"}, allEntries = true)
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
            if (!objectAlreadyExists(SportObjectEnum.FIXTURE, fixture.getId(), null)) {
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
                log.info("Fixture {} stored in database.", fixture.getEvent());
                continue;
            }
            log.debug("Fixture {} already exists in database.", fixture.getEvent());
        }
    }

    public void persistStanding(List<Standing> standings) {
        if (standings.isEmpty()) {
            return;
        }

        String queryPersistStandings = "INSERT INTO football.standing " +
                "(rank, team, points, goals_diff, form, league_id, season, position_description, actual_results_id, home_results_id, away_results_id, updated) " +
                "VALUES(:rank, :team, :points, :goalsDiff, :form, :leagueId, :season, :additionalPositionDescription, :resultsId, :homeResultsId, :awayResultsId, :updated)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for (Standing standing : standings) {
            parameters.addValue("updated", standing.getUpdated());
            parameters.addValue("team", standing.getTeam().getName());
            if (!objectAlreadyExists(SportObjectEnum.STANDING, standing.getUpdated(), standing.getTeam().getName())) {
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
                log.info("{}s standing updated in database.", standing.getTeam().getName());
                continue;
            }
            log.debug("{}s standing already exists in database.", standing.getTeam().getName());
        }
    }

    public void persistFixtureStats(Map<Integer, Map<Integer, FixtureStatistics>> fixtureStatistics) {
        Integer fixtureId = fixtureStatistics.keySet().stream().findFirst().orElse(null);
        List<Integer> teamIds = fixtureStatistics.get(fixtureId).keySet().stream().toList();
        List<String> queriesPersistFixturesStats = glueQueryForStatistics(fixtureStatistics.get(fixtureId), fixtureId);
        for (int i = 0; i < teamIds.size(); i++) {
            if (!objectAlreadyExists(SportObjectEnum.FIXTURE_STATS, fixtureId, teamIds.get(i))) {
                this.namedParameterJdbcTemplate.update(queriesPersistFixturesStats.get(i), new MapSqlParameterSource());
                log.info("Fixture stats with fixture id {} and team id {} stored in database.", fixtureId, teamIds.get(i));
            } else {
                log.debug("Fixture id {} already exists in database.", fixtureId);
            }
        }
    }

    public void persistCoach(Coach coach) {
        if (coach == null) {
            return;
        }

        if (objectAlreadyExists(SportObjectEnum.COACH, null, coach.getExternalId())) {
            log.debug("Coach {} with external id {} already exists in database", coach.getName(), coach.getExternalId());
            return;
        }

        String queryPersistCoach = "INSERT INTO football.coach (external_id, name, first_name, last_name, age, birth_date, birth_country, nationality, height, weight, photo) " +
                "VALUES(:externalId, :name, :firstName, :lastName, :age, :birthDate, :birthCountry, :nationality, :height, :weight, :photo)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("externalId", coach.getExternalId());
        parameters.addValue("name", coach.getName());
        parameters.addValue("firstName", coach.getFirstName());
        parameters.addValue("lastName", coach.getLastName());
        parameters.addValue("age", coach.getAge());
        parameters.addValue("birthDate", coach.getBirthDate());
        parameters.addValue("birthCountry", coach.getBirthCountry());
        parameters.addValue("nationality", coach.getNationality());
        parameters.addValue("height", coach.getHeight());
        parameters.addValue("weight", coach.getWeight());
        parameters.addValue("photo", coach.getPhoto());

        this.namedParameterJdbcTemplate.update(queryPersistCoach, parameters);
        log.info("Coach {} stored in database.", coach.getName());
    }

    public void persistCoachCareer(Map<Integer, List<CoachHistory>> coachHistory) {
        Integer coachId = coachHistory.keySet().iterator().next();
        String queryPersistCoachCareer = "INSERT INTO football.coach_career (coach_id, team_id, start, \"end\") " +
                "VALUES(:coachId, :teamId, :start, :end)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        Integer teamId;
        for (List<CoachHistory> historyList : coachHistory.values()) {
            for (CoachHistory history : historyList) {
                teamId = history.getTeam().getExternalTeamId();
                if (!coachCareerObjectAlreadyExists(coachId, history)) {
                    parameters.addValue("coachId", coachId);
                    parameters.addValue("teamId", teamId);
                    parameters.addValue("start", history.getStart());
                    parameters.addValue("end", history.getEnd());
                    this.namedParameterJdbcTemplate.update(queryPersistCoachCareer, parameters);
                    log.info("Team with id {} added to coach (with id {}) career history.", teamId, coachId);
                } else {
                    log.debug("The coach with id {} already has a career entry for team {} that matches the rest of the provided data.", coachId, history.getTeam().getName());
                }
            }
        }
    }

    private Integer prepareResults(MapSqlParameterSource parameters, Results results) {

        String queryPersistResults = "INSERT INTO football.results (team_id, rounds_played, wins, draws, loses, goals_for, goals_against, description) " +
                "VALUES(:teamId, :roundsPlayed, :wins, :draws, :loses, :goalsFor, :goalsAgainst, :description) " +
                "RETURNING id";

        String queryGetResultId = "SELECT id FROM football.results WHERE description = :description AND team_id = :teamId";

        parameters.addValue("description", results.getDescription());
        parameters.addValue("teamId", results.getTeam().getExternalTeamId());
        if (!objectAlreadyExists(SportObjectEnum.RESULTS, results.getDescription(), results.getTeam().getExternalTeamId())) {
            parameters.addValue("roundsPlayed", results.getRoundsPlayed());
            parameters.addValue("wins", results.getWins());
            parameters.addValue("draws", results.getDraws());
            parameters.addValue("loses", results.getLoses());
            parameters.addValue("goalsFor", results.getGoals().getOrDefault("FOR", 0));
            parameters.addValue("goalsAgainst", results.getGoals().getOrDefault("AGAINST", 0));

            log.info("{}s results stored in database.", results.getTeam().getName());
            return this.namedParameterJdbcTemplate.queryForObject(queryPersistResults, parameters, Integer.class);
        } else {
            log.info("{}s results updated in database.", results.getTeam().getName());
            return this.namedParameterJdbcTemplate.queryForObject(queryGetResultId, parameters, Integer.class);
        }
    }

    private List<String> glueQueryForStatistics(Map<Integer, FixtureStatistics> fixtureStatistics, Integer fixtureId) {
        List<String> queries = new ArrayList<>();
        for (var entry : fixtureStatistics.entrySet()) {
            StringBuilder firstPartOfQuery = new StringBuilder("INSERT INTO football.fixture_statistics (fixture_id");
            StringBuilder secondPartOfQuery = new StringBuilder(" VALUES(" + fixtureId);
            firstPartOfQuery
                    .append(", team_id");
            secondPartOfQuery
                    .append(", ")
                    .append(entry.getKey());
            for (var specificTeamStats : entry.getValue().getFixtureStatistics().entrySet()) {
                firstPartOfQuery
                        .append(", ")
                        .append(specificTeamStats.getKey().getDatabaseColumnName());
                secondPartOfQuery
                        .append(", ");
                if (specificTeamStats.getKey().getValueType() == String.class) {
                    secondPartOfQuery
                            .append("'")
                            .append(specificTeamStats.getValue())
                            .append("'");
                } else {
                    secondPartOfQuery
                            .append(specificTeamStats.getValue());
                }
            }
            firstPartOfQuery
                    .append(")");
            secondPartOfQuery
                    .append(")");
            queries.add(firstPartOfQuery + secondPartOfQuery.toString());
        }
        return queries;
    }

    private boolean objectAlreadyExists(SportObjectEnum object, Object internalId, Object externalId) {
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

    /**
     * There is a need to implement custom checking for objects of type SportObjectEnum.COACH_HISTORY because more parameters are required
     * to determine if a particular record already exists in the database. To illustrate this, we can take the example of coach Carlo Ancelotti,
     * who had two stints at Real Madrid from 2013 to 2015 and from 2021 to 2024. So, in addition to checking based on the coach's ID and
     * the team's ID, the next step is to check based on the time periods when the coach was in charge of the particular team.
     */

    private boolean coachCareerObjectAlreadyExists(Integer coachId, CoachHistory coachHistory) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("coachId", coachId);
        parameters.addValue("start", coachHistory.getStart());
        parameters.addValue("end", coachHistory.getEnd());
        parameters.addValue("teamId", coachHistory.getTeam().getExternalTeamId());
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(
                queriesForChecking.get(SportObjectEnum.COACH_HISTORY).get(0),
                parameters,
                Boolean.class));
    }
}
