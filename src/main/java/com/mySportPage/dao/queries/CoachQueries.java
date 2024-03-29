package com.mySportPage.dao.queries;

import lombok.Getter;

@Getter
public enum CoachQueries {
    AND("AND "),

    CORE_COLUMNS("t.league_id , c.name, c.first_name, c.last_name, c.age, c.birth_date, c.nationality, c.photo, cc.team_id "),

    CORE_QUERY("SELECT " + CORE_COLUMNS.getQuery() +
            "FROM football.coach c " +
            "LEFT JOIN football.coach_career cc on c.external_id = cc.coach_id " +
            "LEFT JOIN football.team t on t.team_id = cc.team_id " +
            "LEFT JOIN public.country c2 on c2.name = c.birth_country " +
            "WHERE TRUE "),

    GET_COACH_BY_LEAGUE_ID("t.league_id = :leagueId "),

    GET_COACH_BY_TEAM_ID("t.team_id  = :teamId "),

    GET_COACH_BY_COUNTRY("c2.name = :country "),

    GET_ALL_COACHES("SELECT " + CORE_COLUMNS.getQuery() +
            "FROM football.coach c " +
            "LEFT OUTER JOIN ( " +
            "    SELECT cc.*, " +
            "           ROW_NUMBER() OVER (PARTITION BY cc.coach_id ORDER BY cc.end DESC) AS rn " +
            "    FROM football.coach_career cc " +
            "    WHERE cc.end IS  NULL OR cc.end < NOW() " +
            ") cc ON c.external_id = cc.coach_id AND cc.rn = 1 " +
            "LEFT JOIN football.team t ON t.team_id = cc.team_id " +
            "LEFT JOIN public.country c2 ON c2.name = c.birth_country");

    private final String query;

    CoachQueries(String query) {
        this.query = query;
    }
}
