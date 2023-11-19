package com.mySportPage.rest.path.internal;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;

public interface StadiumRestPath {

    String ROOT_PATH = "/stadium";
    String GET_BY_CITY = "/city/{" + CITY + "}";
    String GET_BY_ADDRESS = "/address/{" + ADDRESS + "}";
    String GET_BY_TEAM_ID = "/team-id/{" + TEAM_ID + "}";
    String GET_BY_TEAM_NAME = "/team-name/{" + TEAM_NAME + "}";
    String GET_BY_NAME = "/name/{" + NAME + "}";
}
