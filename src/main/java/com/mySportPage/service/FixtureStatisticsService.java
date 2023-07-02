package com.mySportPage.service;

import com.mySportPage.model.FixtureStatistics;
import com.mySportPage.model.SportEnum;

import java.util.Map;

public interface FixtureStatisticsService {

    Map<Integer, FixtureStatistics> getFixtureStatistics(Integer fixtureId, SportEnum sportEnum);

}
