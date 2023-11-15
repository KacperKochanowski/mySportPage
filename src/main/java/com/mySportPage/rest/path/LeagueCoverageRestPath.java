package com.mySportPage.rest.path;

import static com.mySportPage.rest.path.LeagueCoverageRestPath.PathParams.LEAGUE_ID;

public interface LeagueCoverageRestPath {

    String ROOT_PATH = "/coverage";
    String GET_LEAGUE_COVERAGE_BY_SPORT = "/";
    String GET_LEAGUE_COVERAGE_BY_LEAGUE_AND_SPORT = "/leagueId/" + LEAGUE_ID;

    interface PathParams {
        String LEAGUE_ID = "{leagueId}";
    }

    interface QueryParams {
        String SPORT_ID = "sportId";
    }
}
