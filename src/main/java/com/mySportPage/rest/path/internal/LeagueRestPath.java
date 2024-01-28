package com.mySportPage.rest.path.internal;

import static com.mySportPage.rest.path.internal.CommonRestParams.COUNTRY;

public interface LeagueRestPath {

    String ROOT_PATH = "/leagues";
    String GET_ALL = "/all";
    String GET_BY_COUNTRY = "/country/{" + COUNTRY + "}";
    String ANY_PLAYS = "/any-plays";
}
