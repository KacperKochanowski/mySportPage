package com.mySportPage.dao.queries;

import lombok.Getter;

@Getter
public enum CoachQueries {
    AND(" AND "),

    CORE_COLUMNS("DISTINCT(c.external_id), c.name, c.first_name, c.last_name, c.age, c.birth_date, c.nationality, c.height, c.weight, c.photo "),

    CORE_QUERY("SELECT " + CORE_COLUMNS.getQuery() +
                           "FROM football.coach c " +
                           "LEFT JOIN football.coach_career cc on c.external_id = cc.coach_id " +
                           "LEFT JOIN football.team t on t.team_id = cc.team_id " +
                           "LEFT JOIN public.country c2 on c2.name = c.birth_country " +
                           "WHERE "),

    GET_COACH_BY_LEAGUE_ID(" t.league_id = :leagueId"),

    GET_COACH_BY_TEAM_ID(" t.team_id  = :teamId"),

    GET_COACH_BY_COUNTRY_CODE(" c2.code = :countryCode");

    private final String query;

    CoachQueries(String query) {
        this.query = query;
    }
}
