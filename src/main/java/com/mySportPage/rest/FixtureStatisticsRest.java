package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.FixtureStatisticsResponse;
import com.mySportPage.service.FixtureStatisticsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fixture/statistics")
public class FixtureStatisticsRest {

    @Autowired
    private FixtureStatisticsImpl fixtureStatistics;


    @GetMapping("fixture-id/{fixtureId}")
    private FixtureStatisticsResponse fixtureStatistics(@PathVariable Integer fixtureId,
                                                        @RequestParam("sportId") Integer sportId) {
        return FixtureStatisticsResponse.builder()
                .withFixtureStatistics(fixtureStatistics.getFixtureStatistics(fixtureId, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    //TODO: add fixture stats for only one team

}
