package com.mySportPage.dao.queries;

import lombok.Getter;

@Getter
public enum CoachQueries {
    CORE_COLUMNS("c.external_id, c.name, c.first_name, c.last_name, c.age, c.birth_date, c.birth_country, c.nationality, c.height, c.weight, c.photo "),
    GET_COACH_BY_LEAGUE_ID("SELECT " + CORE_COLUMNS.getQuery() +
                           "FROM {schema}.coach c " +
                           "LEFT JOIN {schema}.coach_career cc on c.external_id = cc.coach_id " +
                           "LEFT JOIN {schema}.team t on t.team_id = cc.team_id " +
                           "WHERE t.league_id = :leagueId");

    private final String query;

    CoachQueries(String query) {
        this.query = query;
    }
}
