package com.mySportPage.controller;

import com.mySportPage.service.FixtureStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class FixtureStatisticsController {

    private final FixtureStatisticsService service;

    @Autowired
    public FixtureStatisticsController(FixtureStatisticsService service) {
        this.service = service;
    }

    public Map<Integer, Map<String, Object>> getFixtureStatistics(Integer fixtureId, Integer teamId) {
        Map<Integer, Map<String, Object>> result = service.getFixtureStatistics(fixtureId);
        return teamId == null ? result :
                result.entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().equals(teamId))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
}
