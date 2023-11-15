package com.mySportPage.rest.path;

import static com.mySportPage.rest.path.FixtureStatisticsRestPath.PathParams.FIXTURE_ID;

public interface FixtureStatisticsRestPath {

    String ROOT_PATH = "fixture/statistics";
    String GET_BY_FIXTURE_ID = "/id/" + FIXTURE_ID;

    interface PathParams {
        String FIXTURE_ID = "{fixtureId}";
    }
}
