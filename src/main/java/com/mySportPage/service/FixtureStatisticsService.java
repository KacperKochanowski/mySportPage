package com.mySportPage.service;

import java.util.Map;

public interface FixtureStatisticsService {

    Map<Integer, Map<String, Object>> getFixtureStatistics(Integer fixtureId);

}
