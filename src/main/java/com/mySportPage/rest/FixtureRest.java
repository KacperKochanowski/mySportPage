package com.mySportPage.rest;

import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.mySportPage.rest.path.internal.FixtureRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class FixtureRest {

    @Autowired
    private FixtureService fixtureService;

    @GetMapping(GET_CURRENT_FIXTURES)
    public SportPageResponse getFixtures() {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_FIXTURES_FOR_TWO_WEEKS)
    public SportPageResponse getFixturesByDateLeagueRound() {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixturesByDateLeagueRound())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_FIXTURES_BY_LEAGUE)
    public SportPageResponse getFixtures(
            @PathVariable("leagueId") Integer leagueId,
            @RequestParam(required = false) Integer round) {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures(leagueId, round))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_FIXTURES_BY_TEAM)
    public SportPageResponse getFixtures(
            @PathVariable("teamId") Integer teamId,
            @RequestParam(required = false) String place) {
        return SportPageResponse.builder()
                .withData(fixtureService.getFixtures(teamId, place))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_FIXTURES_BY_TEAM_AND_IF_PLAYED)
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
