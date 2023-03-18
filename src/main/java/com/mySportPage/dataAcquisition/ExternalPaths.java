package com.mySportPage.dataAcquisition;

import lombok.Getter;

@Getter
public enum ExternalPaths {

    BASE("https://api-football-v1.p.rapidapi.com/v3/"),
    GET_TEAMS_AND_STADIUMS_V3(BASE + "teams"),
    GET_LEAGUES_V3(BASE + "leagues"),
    GET_FIXTURES_V3(BASE + "fixtures");

    private final String url;

    ExternalPaths(String url) {
        this.url = url;
    }
}
