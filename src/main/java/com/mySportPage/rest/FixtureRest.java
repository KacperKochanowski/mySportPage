package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.SportPageResponse;
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
    public SportPageResponse getFixtures(
            @RequestParam("sportId") Integer sportId) {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures(SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
    //TODO check response and field "start": "2024-03-01T23:00:00.000+00:00", make it looks better

    @GetMapping("for-two-weeks")
    public SportPageResponse getFixturesByDateLeagueRound(
            @RequestParam("sportId") Integer sportId) {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixturesByDateLeagueRound(SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("league/{leagueId}")
    public SportPageResponse getFixtures(
            @PathVariable("leagueId") Integer leagueId,
            @RequestParam(required = false) Integer round,
            @RequestParam("sportId") Integer sportId) {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures(leagueId, round, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("team/{teamId}")
    public SportPageResponse getFixtures(
            @PathVariable("teamId") Integer teamId,
            @RequestParam(required = false) String place,
            @RequestParam("sportId") Integer sportId) {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures(teamId, place, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("team/{teamId}/played/{played}")
    public SportPageResponse getFixtures(
            @PathVariable("teamId") Integer teamId,
            @PathVariable("played") boolean played,
            @RequestParam("sportId") Integer sportId) {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures(teamId, played, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
