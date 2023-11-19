package com.mySportPage.rest.path.internal;

import static com.mySportPage.rest.path.internal.CommonRestParams.LEAGUE_ID;

public interface StandingsRestPath {

    String ROOT_PATH = "/standings";
    String GET_BY_LEAGUE_ID = "/leagueId/{" + LEAGUE_ID + "}";
}
