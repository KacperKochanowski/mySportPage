package com.mySportPage.rest.path.internal;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;

public interface FixtureRestPath {
    String ROOT_PATH = "/fixtures";
    String GET_CURRENT_FIXTURES = "/current";
    String GET_FIXTURES_FOR_TWO_WEEKS = "/for-two-weeks";
    String GET_FIXTURES_BY_LEAGUE = "/league/{" + LEAGUE_ID + "}";
}
