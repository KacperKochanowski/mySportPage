package com.mySportPage.fixture.dao;

import lombok.Getter;

@Getter
public enum FixtureQueries {

    CORE_COLUMNS("league_id, event, start, played, result, round "),

    /**
     * GET_FIXTURES is used to fetch fixtures from specific (current) round by every league
     */
    
    GET_FIXTURES("SELECT " + CORE_COLUMNS.getQuery() +
                 "FROM fixture " +
                 "GROUP BY event, start, result, round, league_id, played " +
                 "HAVING round = MIN(CAST((SELECT round FROM fixture WHERE played = false LIMIT 1) AS integer)) "),

    GET_FIXTURES_BY_LEAGUE_ID("SELECT  " + CORE_COLUMNS.getQuery() +
                              "FROM fixture " +
                              "WHERE league_id = :leagueId "),

    GET_FIXTURES_BY_LEAGUE_ID_AND_ROUND_NO(GET_FIXTURES_BY_LEAGUE_ID.getQuery() + "AND round = :round "),

    GET_FIXTURES_BY_TEAM_ID("WITH team_name AS( " +
                            "SELECT name FROM team WHERE team_id = :teamId) " +
                            "SELECT " + CORE_COLUMNS.getQuery() +
                            "FROM fixture f " +
                            "JOIN team_name ON f.host = team_name.name OR f.guest = team_name.name "),

    GET_FIXTURES_BY_TEAM_ID_AND_WHETHER_PLAYED(GET_FIXTURES_BY_TEAM_ID.getQuery() + "WHERE f.played = :played"),

    GET_FIXTURES_BY_TEAM_ID_HOME(GET_FIXTURES_BY_TEAM_ID.getQuery() + "WHERE f.host = team_name.name "),

    GET_FIXTURES_BY_TEAM_ID_AWAY(GET_FIXTURES_BY_TEAM_ID.getQuery() + "WHERE f.guest = team_name.name ");

    private final String query;

    FixtureQueries(String query) {
        this.query = query;
    }
}
