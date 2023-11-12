package com.mySportPage.rest.path;

public interface CoachRestPath {

    String ROOT_PATH = "/coach";
    String GET_COACH_BY_MULTIPLE_PARAMS = "";
    String GET_COACH_BY_LEAGUE = "/leagueId/" + PathParams.LEAGUE_ID;
    String GET_COACH_BY_TEAM = "/teamId/" + PathParams.TEAM_ID;
    String GET_COACH_BY_COUNTRY = "/country/" + PathParams.COUNTRY;

    interface PathParams {
        String LEAGUE_ID = "{leagueId}";
        String TEAM_ID = "{teamId}";
        String COUNTRY = "{country}";
    }

    interface QueryParams {
        String LEAGUE_ID = "leagueId";
        String TEAM_ID = "teamId";
        String COUNTRY = "country";
    }
}
