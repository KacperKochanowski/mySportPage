package com.mySportPage.dao.queries;

import lombok.Getter;

@Getter
public enum CoachQueries {
    CORE_COLUMNS("external_id, name, first_name, last_name, age, birth_date, birth_country, nationality, height, weight, photo "),
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
