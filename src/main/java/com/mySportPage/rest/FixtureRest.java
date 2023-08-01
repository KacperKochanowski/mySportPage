package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.FixtureResponse;
import com.mySportPage.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fixtures")
public class FixtureRest {

    @Autowired
    private FixtureService fixtureService;

    @GetMapping("current")
    public FixtureResponse getFixtures(
            @RequestParam("sportId") Integer sportId) {
        return FixtureResponse.builder()
                .withFixtures(fixtureService.getFixtures(SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
    //TODO check response and field "start": "2024-03-01T23:00:00.000+00:00", make it looks better

    @GetMapping("for-two-weeks")
    public FixtureResponse getFixturesByDateLeagueRound(
            @RequestParam("sportId") Integer sportId) {
        return FixtureResponse.builder()
                .withFixtures(fixtureService.getFixturesByDateLeagueRound(SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("league/{leagueId}")
    public FixtureResponse getFixtures(
            @PathVariable("leagueId") Integer leagueId,
            @RequestParam(required = false) Integer round,
            @RequestParam("sportId") Integer sportId) {
        return FixtureResponse.builder()
                .withFixtures(fixtureService.getFixtures(leagueId, round, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("team/{teamId}")
    public FixtureResponse getFixtures(
            @PathVariable("teamId") Integer teamId,
            @RequestParam(required = false) String place,
            @RequestParam("sportId") Integer sportId) {
        return FixtureResponse.builder()
                .withFixtures(fixtureService.getFixtures(teamId, place, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("team/{teamId}/played/{played}")
    public FixtureResponse getFixtures(
            @PathVariable("teamId") Integer teamId,
            @PathVariable("played") boolean played,
            @RequestParam("sportId") Integer sportId) {
        return FixtureResponse.builder()
                .withFixtures(fixtureService.getFixtures(teamId, played, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
