package com.mySportPage.rest.path.internal;


import static com.mySportPage.rest.path.internal.CommonRestParams.LEAGUE_ID;
import static com.mySportPage.rest.path.internal.CommonRestParams.SPORT_ID;

public interface LeagueCoverageRestPath {

    String ROOT_PATH = "/coverage";
    String GET_LEAGUE_COVERAGE_BY_SPORT = "/sport/{" + SPORT_ID + "}";
    String GET_LEAGUE_COVERAGE_BY_LEAGUE_AND_SPORT = "/leagueId/{" + LEAGUE_ID + "}";
}
