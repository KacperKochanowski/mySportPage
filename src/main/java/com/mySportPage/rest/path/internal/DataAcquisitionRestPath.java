package com.mySportPage.rest.path.internal;

import static com.mySportPage.rest.path.internal.CommonRestParams.TEAM_ID;

public interface DataAcquisitionRestPath {

    String ROOT_PATH = "/internal/data-acquisition/create/";
    String CREATE_TEAMS_AND_STADIUMS = "teams-and-stadiums";
    String CREATE_LEAGUES = "leagues";
    String CREATE_FIXTURES = "fixtures";
    String CREATE_STANDINGS = "standings";
    String CREATE_FIXTURE_STATISTICS = "fixture-statistics";
    String CREATE_COACH_WITH_HISTORY = "coach-with-history/{" + TEAM_ID + "}";
    String CREATE_COUNTRIES = "countries";
}
