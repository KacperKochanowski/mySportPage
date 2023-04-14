package com.mySportPage.league.dao;

import lombok.Getter;

@Getter
public enum LeagueQueries {

    GET_LEAGUES("SELECT name, type, country " +
                "FROM league "),

    GET_LEAGUES_BY_COUNTRY(GET_LEAGUES.getQuery() + "WHERE country = :country ");

    private final String query;

    LeagueQueries(String query) {
        this.query = query;
    }
}
