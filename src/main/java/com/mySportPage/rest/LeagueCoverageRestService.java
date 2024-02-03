package com.mySportPage.rest;

import com.mySportPage.controller.LeagueCoverageController;
import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mySportPage.rest.path.internal.CommonRestParams.LEAGUE_ID;
import static com.mySportPage.rest.path.internal.CommonRestParams.SPORT_ID;
import static com.mySportPage.rest.path.internal.LeagueCoverageRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class LeagueCoverageRestService extends AbstractRestService {

    private final LeagueCoverageController controller;

    @Autowired
    public LeagueCoverageRestService(LeagueCoverageController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_LEAGUE_COVERAGE_BY_SPORT)
    public SportPageResponse<List<LeagueCoverage>> getCoverage(
            @PathVariable(SPORT_ID) Integer sportId) {
        return processResponse(() -> controller.getCoverage(sportId));
    }

    @GetMapping(GET_LEAGUE_COVERAGE_BY_LEAGUE_AND_SPORT)
    public SportPageResponse<List<LeagueCoverage>> getCoverage(
            @PathVariable(SPORT_ID) Integer sportId,
            @PathVariable(LEAGUE_ID) Integer leagueId) {
        return processResponse(() -> controller.getCoverage(sportId, leagueId));
    }
}