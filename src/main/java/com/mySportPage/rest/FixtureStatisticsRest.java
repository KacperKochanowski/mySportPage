package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.FixtureStatisticsResponse;
import com.mySportPage.service.FixtureStatisticsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("fixture/statistics")
public class FixtureStatisticsRest {

    @Autowired
    private FixtureStatisticsImpl fixtureStatistics;

    //TODO: fix returned data
    @GetMapping("fixture-id/{fixtureId}")
    private FixtureStatisticsResponse fixtureStatistics(@PathVariable Integer fixtureId,
                                                        @RequestParam("sportId") Integer sportId,
                                                        @RequestParam(required = false) Integer teamId) {
        Map<Integer, Map<String, Object>> fixtureStats = fixtureStatistics.getFixtureStatistics(fixtureId, SportEnum.getById(sportId));
        return FixtureStatisticsResponse.builder()
                .withFixtureStatistics(teamId != null ? fixtureStats.get(teamId) : fixtureStats)
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
