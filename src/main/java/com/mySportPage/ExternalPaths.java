package com.mySportPage;

import lombok.Getter;

@Getter
public enum ExternalPaths {

    BASE("https://api-football-v1.p.rapidapi.com/v3/"),
    GET_TEAMS_AND_STADIUMS_V3(BASE.getUrl() + "teams"),
    GET_LEAGUES_V3(BASE.getUrl() + "leagues"),
    GET_FIXTURES_V3(BASE.getUrl() + "fixtures"),
    GET_STANDINGS_V3(BASE.getUrl() + "standings?season={season}"),
    GET_FIXTURES_STATISTICS_V3(BASE.getUrl() + "fixtures/statistics");

    private final String url;

    ExternalPaths(String url) {
        this.url = url;
    }
}
