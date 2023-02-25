package com.mySportPage.dataAcquisition;

import lombok.Getter;

@Getter
public enum ExternalPaths {

    GET_ALL_TEAMS_FROM_ONE_LEAGUE_ID_V2("https://api-football-v1.p.rapidapi.com/v2/teams/league/{league_id}");

    private final String url;

    ExternalPaths(String url) {
        this.url = url;
    }
}
