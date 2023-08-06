package com.mySportPage.rest;

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
    public SportPageResponse getFixtures() {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("for-two-weeks")
    public SportPageResponse getFixturesByDateLeagueRound() {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixturesByDateLeagueRound())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("league/{leagueId}")
    public SportPageResponse getFixtures(
            @PathVariable("leagueId") Integer leagueId,
            @RequestParam(required = false) Integer round) {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures(leagueId, round))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("team/{teamId}")
    public SportPageResponse getFixtures(
            @PathVariable("teamId") Integer teamId,
            @RequestParam(required = false) String place) {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures(teamId, place))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("team/{teamId}/played/{played}")
    public SportPageResponse getFixtures(
            @PathVariable("teamId") Integer teamId,
            @PathVariable("played") boolean played) {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures(teamId, played))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
