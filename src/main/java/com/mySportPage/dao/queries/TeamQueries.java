package com.mySportPage.dao.queries;

import lombok.Getter;

@Getter
public enum TeamQueries {

    CORE_QUERY("SELECT name, shortcut, club_founded, country, league_id " +
            "FROM {schema}.team " +
            "WHERE "),

    GET_TEAM_BY_TEAM_ID(CORE_QUERY.getQuery() +
            " team_id = :teamId "),

    GET_TEAM_BY_LEAGUE_ID(CORE_QUERY.getQuery() +
            " league_id = :leagueId "),

    GET_TEAM_BY_COUNTRY_NAME_ID(CORE_QUERY.getQuery() +
            " country ILIKE :countryName");

    private final String query;

    TeamQueries(String query) {
        this.query = query;
    }
}
