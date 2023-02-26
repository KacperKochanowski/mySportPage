package com.mySportPage.dataAcquisition;

import lombok.Getter;

@Getter
public enum ExternalPaths {

    GET_ALL_TEAMS_FROM_ONE_LEAGUE_ID_V3("https://api-football-v1.p.rapidapi.com/v3/teams");

    private final String url;

    ExternalPaths(String url) {
        this.url = url;
    }
}
