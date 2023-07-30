package com.mySportPage.dao.queries;

import lombok.Getter;

@Getter
public enum TeamQueries {

    GET_TEAM_BY_TEAM_ID("SELECT name, shortcut, club_founded, country, league_id " +
                        "FROM {schema}.team " +
                        "WHERE team_id = :teamId ");

    private final String query;

    TeamQueries(String query) {
        this.query = query;
    }
}
