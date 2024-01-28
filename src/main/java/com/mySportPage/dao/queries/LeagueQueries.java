package com.mySportPage.dao.queries;

import lombok.Getter;

@Getter
public enum LeagueQueries {

    GET_LEAGUES("SELECT name, type, country " +
                "FROM football.league "),

    GET_LEAGUES_BY_COUNTRY(GET_LEAGUES.getQuery() + "WHERE country = :country "),

    ANY_LEAGUE_PLAYS("SELECT EXISTS (SELECT 1 FROM football.league l WHERE l.end > NOW())");

    private final String query;

    LeagueQueries(String query) {
        this.query = query;
    }
}
