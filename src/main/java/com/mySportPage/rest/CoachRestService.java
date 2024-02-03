package com.mySportPage.rest;

import com.mySportPage.controller.CoachController;
import com.mySportPage.model.Coach;
import com.mySportPage.model.request.CoachRequestModel;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mySportPage.rest.path.internal.CoachRestPath.*;
import static com.mySportPage.rest.path.internal.CommonRestParams.*;

@RestController
@RequestMapping(ROOT_PATH)
public class CoachRestService extends AbstractRestService {

    private final CoachController controller;

    @Autowired
    public CoachRestService(CoachController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_COACH_BY_LEAGUE)
    public SportPageResponse<List<Coach>> getCoachByLeague(
            @PathVariable(LEAGUE_ID) Integer leagueId) {
        return processResponse(() -> controller.getCoachesByLeague(leagueId));
    }

    @GetMapping(GET_COACH_BY_TEAM)
    public SportPageResponse<List<Coach>> getCoachByTeam(
            @PathVariable(TEAM_ID) Integer teamId) {
        return processResponse(() -> controller.getCoachesByTeam(teamId));
    }

    @GetMapping(GET_COACH_BY_COUNTRY)
    public SportPageResponse<List<Coach>> getCoachByCountry(
            @PathVariable(COUNTRY) String country) {
        return processResponse(() -> controller.getCoachesByCountry(country));
    }

    @PostMapping()
    public SportPageResponse<List<Coach>> getCoachByMultipleParams(
            @RequestBody CoachRequestModel requestModel) {
        return processResponse(() -> controller.getCoaches(requestModel));
    }
}
