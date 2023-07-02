package com.mySportPage.service;

import com.mySportPage.dao.FixtureStatisticsDao;
import com.mySportPage.model.FixtureStatistics;
import com.mySportPage.model.SportEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FixtureStatisticsImpl implements FixtureStatisticsService{

    @Autowired
    private FixtureStatisticsDao fixtureStatisticsDao;

    @Override
    public Map<Integer, FixtureStatistics> getFixtureStatistics(Integer fixtureId, SportEnum sportEnum) {
        return fixtureStatisticsDao.getFixtureStatistics(fixtureId, sportEnum.getSchema());
    }
}
