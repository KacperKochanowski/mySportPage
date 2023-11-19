package com.mySportPage.rest.path.internal;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;

public interface TeamRestPath {

    String ROOT_PATH = "/teams";
    String GET_BY_TEAM_ID = "/team-id/{" + TEAM_ID + "}";
    String GET_BY_LEAGUE_ID = "/league-id/{" + LEAGUE_ID + "}";
    String GET_BY_COUNTRY_NAME = "/country-name/{" + COUNTRY_NAME + "}";
}
