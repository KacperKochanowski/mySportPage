package com.mySportPage.rest;

import com.mySportPage.controller.CoachController;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.mySportPage.rest.path.internal.CoachRestPath.*;
import static com.mySportPage.rest.path.internal.CommonRestParams.*;

@RestController
@RequestMapping(ROOT_PATH)
public class CoachRestService {

    @Autowired
    private CoachController controller;

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

    @GetMapping(GET_COACH_BY_MULTIPLE_PARAMS)
    public SportPageResponse getCoach(
            @RequestParam(required = false) Integer leagueId,
            @RequestParam(required = false) Integer teamId,
            @RequestParam(required = false) String country) {
        if (leagueId == null && teamId == null && country == null) {
            return SportPageResponse.builder()
                    .withData("No search parameters")
                    .withCode(HttpStatus.BAD_REQUEST.value())
                    .withMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .build();
        }
        return SportPageResponse.builder()
                .withData(controller.getCoaches(prepareParams(leagueId, teamId, country)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    //TODO: przerobić to niżej na obiekt klasy customowej jak w market-api
    private Map<String, Object> prepareParams(Integer leagueId, Integer teamId, String country) {
        Map<String, Object> paramMap = new HashMap<>();
        if (leagueId != null) {
            paramMap.put("leagueId", leagueId);
        }
        if (teamId != null) {
            paramMap.put("teamId", teamId);
        }
        if (country != null) {
            paramMap.put("country", country);
        }
        return paramMap;
    }
}
