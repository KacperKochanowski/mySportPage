package com.mySportPage.dataAcquisition;

import lombok.Getter;

@Getter
public enum ExternalPaths {

    GET_TEAMS_AND_STADIUMS_V3("https://api-football-v1.p.rapidapi.com/v3/teams"),
    GET_LEAGUES_V3("https://api-football-v1.p.rapidapi.com/v3/leagues");

    private final String url;

    ExternalPaths(String url) {
        this.url = url;
    }
}
