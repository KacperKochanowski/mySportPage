package com.mySportPage.service;

import com.mySportPage.model.SportEnum;

import java.util.Map;

public interface FixtureStatisticsService {

    Map<Integer, Map<String, Object>> getFixtureStatistics(Integer fixtureId, SportEnum sportEnum);

}
