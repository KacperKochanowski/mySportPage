package com.mySportPage.dao.queries;

import lombok.Getter;

@Getter
public enum FixtureQueries {

    CORE_COLUMNS("f.league_id, l.name, f.event, f.start, f.played, f.result, f.round "),

    /**
     * GET_FIXTURES is used to fetch fixtures from specific (current) round by every league
     */

    GET_FIXTURES("SELECT " + CORE_COLUMNS.getQuery() +
            "FROM football.fixture f " +
            "JOIN football.league l ON f.league_id = l.league_id " +
            "GROUP BY f.event, f.start, f.result, f.round, f.league_id, f.played, l.name " +
            "HAVING f.round = MIN(CAST((SELECT round FROM football.fixture WHERE played = false LIMIT 1) AS integer)) "),

    GET_FIXTURES_FOR_LAST_AND_NEXT_WEEK("SELECT " + CORE_COLUMNS.getQuery() +
            "FROM football.fixture f " +
            "JOIN football.league l ON f.league_id = l.league_id " +
            "WHERE f.start >= date(now() - interval '7 day') " +
            "AND f.start < date(now() + interval '8 day') " +
            "ORDER BY f.start, f.league_id, f.round "),

    GET_FIXTURES_BY_LEAGUE_ID("SELECT  " + CORE_COLUMNS.getQuery() +
            "FROM football.fixture f " +
            "JOIN football.league l ON f.league_id = l.league_id " +
            "WHERE f.league_id = :leagueId "),

    GET_FIXTURES_BY_LEAGUE_ID_AND_ROUND_NO(GET_FIXTURES_BY_LEAGUE_ID.getQuery() + "AND f.round = :round "),

    GET_FIXTURES_BY_TEAM_ID("WITH team_name AS( " +
            "SELECT name FROM football.team WHERE team_id = :teamId) " +
            "SELECT " + CORE_COLUMNS.getQuery() +
            "FROM football.fixture f " +
            "JOIN football.league l ON f.league_id = l.league_id " +
            "JOIN team_name ON f.host = team_name.name OR f.guest = team_name.name "),

    GET_FIXTURES_BY_TEAM_ID_AND_WHETHER_PLAYED(GET_FIXTURES_BY_TEAM_ID.getQuery() + "WHERE f.played = :played "),

    GET_FIXTURES_BY_TEAM_ID_HOME(GET_FIXTURES_BY_TEAM_ID.getQuery() + "WHERE f.host = team_name.name "),

    GET_FIXTURES_BY_TEAM_ID_AWAY(GET_FIXTURES_BY_TEAM_ID.getQuery() + "WHERE f.guest = team_name.name "),

    ANY_MISSING_RESULT("SELECT CAST(COUNT(*) AS integer) " +
            "FROM football.fixture f " +
            "WHERE f.start < NOW() - INTERVAL '3 hours' " +
            "AND f.result IS NULL");

    private final String query;

    FixtureQueries(String query) {
        this.query = query;
    }
}
