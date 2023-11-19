package com.mySportPage.rest.path.internal;

import static com.mySportPage.rest.path.internal.CommonRestParams.FIXTURE_ID;

public interface FixtureStatisticsRestPath {

    String ROOT_PATH = "fixture/statistics";
    String GET_BY_FIXTURE_ID = "/id/{" + FIXTURE_ID + "}";
}
