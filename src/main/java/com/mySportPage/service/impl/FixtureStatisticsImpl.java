package com.mySportPage.service.impl;

import com.mySportPage.dao.FixtureStatisticsDao;
import com.mySportPage.model.SportEnum;
import com.mySportPage.service.FixtureStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FixtureStatisticsImpl implements FixtureStatisticsService {

    @Autowired
    private FixtureStatisticsDao fixtureStatisticsDao;

    @Override
    public Map<Integer, Map<String, Object>> getFixtureStatistics(Integer fixtureId, SportEnum sportEnum) {
        return fixtureStatisticsDao.getFixtureStatistics(fixtureId, sportEnum.getSchema());
    }
}
