package com.mySportPage.rest.path.internal;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;

public interface CoachRestPath {

    String ROOT_PATH = "/coach";
    String GET_COACH_BY_LEAGUE = "/leagueId/{" + LEAGUE_ID + "}";
    String GET_COACH_BY_TEAM = "/teamId/{" + TEAM_ID + "}";
    String GET_COACH_BY_COUNTRY = "/country/{" + COUNTRY + "}";
}
