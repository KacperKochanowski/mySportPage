package com.mySportPage.team.dao;

import lombok.Getter;

@Getter
public enum TeamQueries {

    GET_TEAM_BY_TEAM_ID("SELECT name, shortcut, club_founded, country " +
                        "FROM team " +
                        "WHERE team_id = :teamId ");


    private final String query;

    TeamQueries(String query) {
        this.query = query;
    }
}
