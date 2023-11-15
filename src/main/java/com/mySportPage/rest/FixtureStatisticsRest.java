package com.mySportPage.rest;

import com.mySportPage.rest.path.FixtureStatisticsRestPath;
import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.FixtureStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(FixtureStatisticsRestPath.ROOT_PATH)
public class FixtureStatisticsRest {

    @Autowired
    private FixtureStatisticsService fixtureStatisticsService;

    @GetMapping(FixtureStatisticsRestPath.GET_BY_FIXTURE_ID)
    private SportPageResponse fixtureStatistics(
            @PathVariable Integer fixtureId,
            @RequestParam(required = false) Integer teamId) {
        Map<Integer, Map<String, Object>> fixtureStats = fixtureStatisticsService.getFixtureStatistics(fixtureId);
        return SportPageResponse.builder()
                .withData(teamId != null ? fixtureStats.get(teamId) : fixtureStats)
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
