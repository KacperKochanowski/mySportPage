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
            "WHERE f.round = (SELECT DISTINCT round " +
            "FROM football.fixture f1 " +
            "WHERE played = false " +
            "AND NOT EXISTS ( " +
            "    SELECT 1 " +
            "    FROM football.fixture f2 " +
            "    WHERE f1.round = f2.round " +
            "    AND is_postponed = true " +
            ") " +
            "ORDER BY round ASC " +
            "LIMIT 1) " +
            "GROUP BY f.event, f.start, f.result, f.round, f.league_id, f.played, l.name "),

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
            "JOIN team_name ON f.host = team_name.name OR f.guest = team_name.name " +
            "WHERE TRUE "
    ),

    GET_FIXTURES_BY_TEAM_ID_AND_WHETHER_PLAYED("AND f.played = :played "),

    GET_FIXTURES_BY_TEAM_ID_HOME("AND f.host = team_name.name "),

    GET_FIXTURES_BY_TEAM_ID_AWAY("AND f.guest = team_name.name "),

    ANY_MISSING_RESULT("SELECT CAST(COUNT(*) AS integer) " +
            "FROM football.fixture f " +
            "WHERE f.start < NOW() - INTERVAL '3 hours' " +
            "AND f.result IS NULL " +
            "AND f.is_postponed <> TRUE"),

    MISSING_RESULTS_WITH_LEAGUE("SELECT f.league_id, " +
            "CAST('20' || SUBSTRING(f.season FROM 1 FOR 2) AS int) AS season, " +
            "CAST(COUNT(*) AS INT) " +
            "FROM football.fixture f " +
            "WHERE f.start < NOW() - INTERVAL '3 hours' " +
            "AND f.result IS NULL " +
            "AND f.is_postponed <> TRUE " +
            "GROUP BY league_id, season"),

    SET_POSTPONED_FIXTURES("WITH postponed_fixtures AS ( " +
            "SELECT f.fixture_id AS f_id " +
            "FROM football.fixture f " +
            "WHERE f.\"start\" < NOW() - INTERVAL '3 day' " +
            "AND f.played = false " +
            "AND f.is_postponed != true) " +
            "UPDATE football.fixture " +
            "SET is_postponed = true " +
            "FROM postponed_fixtures " +
            "WHERE fixture_id = postponed_fixtures.f_id");

    private final String query;

    FixtureQueries(String query) {
        this.query = query;
    }
}
