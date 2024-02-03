package com.mySportPage.rest;

import com.mySportPage.controller.FixtureStatisticsController;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.mySportPage.rest.path.internal.CommonRestParams.FIXTURE_ID;
import static com.mySportPage.rest.path.internal.FixtureStatisticsRestPath.GET_BY_FIXTURE_ID;
import static com.mySportPage.rest.path.internal.FixtureStatisticsRestPath.ROOT_PATH;

@RestController
@RequestMapping(ROOT_PATH)
public class FixtureStatisticsRestService extends AbstractRestService {

    private final FixtureStatisticsController controller;

    @Autowired
    public FixtureStatisticsRestService(FixtureStatisticsController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_BY_FIXTURE_ID)
    private SportPageResponse<Map<Integer, Map<String, Object>>> fixtureStatistics(
            @PathVariable(FIXTURE_ID) Integer fixtureId,
            @RequestParam(required = false) Integer teamId) {
        return processResponse(() -> controller.getFixtureStatistics(fixtureId, teamId));
    }
}
