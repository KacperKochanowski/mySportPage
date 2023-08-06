package com.mySportPage.rest;

import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("coach")
public class CoachRest {

    @Autowired
    private CoachService coachService;

    @GetMapping("leagueId/{leagueId}")
    private SportPageResponse getCoachesByLeague(
            @PathVariable Integer leagueId) {
        return SportPageResponse.builder()
                .withData(coachService.getCoachesByLeague(leagueId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("teamId/{teamId}")
    private SportPageResponse getCoachesByTeam(
            @PathVariable Integer teamId) {
        return SportPageResponse.builder()
                .withData(coachService.getCoachesByTeam(teamId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("countryCode/{countryCode}")
    private SportPageResponse getCoachesByCountry(
            @PathVariable String countryCode) {
        return SportPageResponse.builder()
                .withData(coachService.getCoachesByCountry(countryCode))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping()
    private SportPageResponse getCoach(
            @RequestParam(required = false) Integer leagueId,
            @RequestParam(required = false) Integer teamId,
            @RequestParam(required = false) String countryCode) {
        if (leagueId == null && teamId == null && countryCode == null) {
            return SportPageResponse.builder()
                    .withData("No search parameters")
                    .withCode(HttpStatus.BAD_REQUEST.value())
                    .withMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .build();
        }
        return SportPageResponse.builder()
                .withData(coachService.getCoaches(prepareParams(leagueId, teamId, countryCode)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    private Map<String, Object> prepareParams(Integer leagueId, Integer teamId, String countryCode) {
        Map<String, Object> paramMap = new HashMap<>();
        if (leagueId != null) {
            paramMap.put("leagueId", leagueId);
        }
        if (teamId != null) {
            paramMap.put("teamId", teamId);
        }
        if (countryCode != null) {
            paramMap.put("countryCode", countryCode);
        }
        return paramMap;
    }
}
