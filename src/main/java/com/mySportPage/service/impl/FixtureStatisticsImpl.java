package com.mySportPage.service.impl;

import com.mySportPage.dao.FixtureStatisticsDao;
import com.mySportPage.service.FixtureStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FixtureStatisticsImpl implements FixtureStatisticsService {

    private final FixtureStatisticsDao dao;

    @Autowired
    public FixtureStatisticsImpl(FixtureStatisticsDao dao) {
        this.dao = dao;
    }

    @Override
    public Map<Integer, Map<String, Object>> getFixtureStatistics(Integer fixtureId) {
        return dao.getFixtureStatistics(fixtureId);
    }
}
