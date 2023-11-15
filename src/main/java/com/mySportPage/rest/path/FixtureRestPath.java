package com.mySportPage.rest.path;

import static com.mySportPage.rest.path.FixtureRestPath.PathParams.*;

public interface FixtureRestPath {
    String ROOT_PATH = "/fixtures";
    String GET_CURRENT_FIXTURES = "/current";
    String GET_FIXTURES_FOR_TWO_WEEKS = "/for-two-weeks";
    String GET_FIXTURES_BY_LEAGUE = "/league/" + LEAGUE_ID;
    String GET_FIXTURES_BY_TEAM = "/team/" + TEAM_ID;
    String GET_FIXTURES_BY_TEAM_AND_IF_PLAYED = "/team/" + TEAM_ID + "/played/" + PLAYED;

    interface PathParams {
        String LEAGUE_ID = "{leagueId}";
        String TEAM_ID = "{teamId}";
        String PLAYED = "{played}";
    }

    interface QueryParams {
        String ROUND = "round";
        String PLACE = "place";
    }
}
