package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.CoachResponse;
import com.mySportPage.service.CoachServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("coach")
public class CoachRest {

    @Autowired
    private CoachServiceImpl coachService;

    @GetMapping("leagueId/{leagueId}")
    private CoachResponse getCoachesByLeague(
            @PathVariable Integer leagueId,
            @RequestParam("sportId") Integer sportId) {
        return CoachResponse.builder()
                .withCoaches(coachService.getCoachesByLeague(leagueId, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("teamId/{teamId}")
    private CoachResponse getCoachesByTeam(
            @PathVariable Integer teamId,
            @RequestParam("sportId") Integer sportId) {
        return CoachResponse.builder()
                .withCoaches(coachService.getCoachesByTeam(teamId, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("countryCode/{countryCode}")
    private CoachResponse getCoachesByCountry(
            @PathVariable String countryCode,
            @RequestParam("sportId") Integer sportId) {
        return CoachResponse.builder()
                .withCoaches(coachService.getCoachesByCountry(countryCode, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping()
    private CoachResponse getCoach(
            @RequestParam("sportId") Integer sportId,
            @RequestParam(required = false) Integer leagueId,
            @RequestParam(required = false) Integer teamId,
            @RequestParam(required = false) String countryCode) {
        if (leagueId == null && teamId == null && countryCode == null) {
            return CoachResponse.builder()
                    .withCoaches("No search parameters")
                    .withCode(HttpStatus.BAD_REQUEST.value())
                    .withMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .build();
        }
        return CoachResponse.builder()
                .withCoaches(coachService.getCoaches(prepareParams(leagueId, teamId, countryCode), SportEnum.getById(sportId)))
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
