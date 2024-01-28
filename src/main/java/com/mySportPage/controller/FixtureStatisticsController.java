package com.mySportPage.controller;

import com.mySportPage.service.FixtureStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class FixtureStatisticsController {

    private final FixtureStatisticsService service;

    @Autowired
    private FixtureStatisticsController(FixtureStatisticsService service) {
        this.service = service;
    }

    public Map<Integer, Map<String, Object>> getFixtureStatistics(Integer fixtureId) {
        return service.getFixtureStatistics(fixtureId);
    }
}
