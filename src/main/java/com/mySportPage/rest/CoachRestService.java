package com.mySportPage.rest;

import com.mySportPage.controller.CoachController;
import com.mySportPage.model.request.CoachRequestModel;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.mySportPage.rest.path.internal.CoachRestPath.*;
import static com.mySportPage.rest.path.internal.CommonRestParams.*;

@RestController
@RequestMapping(ROOT_PATH)
public class CoachRestService {

    private final CoachController controller;

    @Autowired
    public CoachRestService(CoachController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_COACH_BY_LEAGUE)
    public SportPageResponse getCoachByLeague(
            @PathVariable(LEAGUE_ID) Integer leagueId) {
        return SportPageResponse.builder()
                .withData(controller.getCoachesByLeague(leagueId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_COACH_BY_TEAM)
    public SportPageResponse getCoachByTeam(
            @PathVariable(TEAM_ID) Integer teamId) {
        return SportPageResponse.builder()
                .withData(controller.getCoachesByTeam(teamId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_COACH_BY_COUNTRY)
    public SportPageResponse getCoachByCountry(
            @PathVariable(COUNTRY) String country) {
        return SportPageResponse.builder()
                .withData(controller.getCoachesByCountry(country))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @PostMapping()
    public SportPageResponse getCoachByMultipleParams(
            @RequestBody CoachRequestModel requestModel) {
        if (requestModel == null) {
            return SportPageResponse.builder()
                    .withData("No search parameters")
                    .withCode(HttpStatus.BAD_REQUEST.value())
                    .withMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .build();
        }
        return SportPageResponse.builder()
                .withData(controller.getCoaches(requestModel))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
