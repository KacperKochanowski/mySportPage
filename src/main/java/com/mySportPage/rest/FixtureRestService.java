package com.mySportPage.rest;

import com.mySportPage.controller.FixtureController;
import com.mySportPage.model.dto.FixtureDTO;
import com.mySportPage.model.request.FixtureRequestModel;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;
import static com.mySportPage.rest.path.internal.FixtureRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class FixtureRestService extends AbstractRestService {

    private final FixtureController controller;

    @Autowired
    public FixtureRestService(FixtureController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_CURRENT_FIXTURES)
    public SportPageResponse<List<FixtureDTO>> getFixtures() {
        return processResponse(controller::getFixtures);
    }

    @GetMapping(GET_FIXTURES_FOR_TWO_WEEKS)
    public SportPageResponse<Map<String, Map<String, Map<String, List<FixtureDTO>>>>> getFixturesByDateLeagueRound() {
        return processResponse(controller::getFixturesByDateLeagueRound);
    }

    @GetMapping(GET_FIXTURES_BY_LEAGUE)
    public SportPageResponse<Map<String, List<FixtureDTO>>> getFixturesByLeague(
            @PathVariable(LEAGUE_ID) Integer leagueId,
            @RequestParam(required = false) Integer round) {
        return processResponse(() -> controller.getFixtures(leagueId, round));
    }

    @PostMapping()
    public SportPageResponse<List<FixtureDTO>> getFixturesByManyParams(
            @RequestBody FixtureRequestModel requestModel) {
        return processResponse(() -> controller.getFixtures(requestModel));
    }
}
