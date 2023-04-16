package com.mySportPage.fixture.rest;

import com.mySportPage.fixture.dto.FixtureDTO;
import com.mySportPage.fixture.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("fixtures")
public class FixtureRest {

    @Autowired
    private FixtureService fixtureService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("current")
    public List<FixtureDTO> getFixtures() {
        return fixtureService.getFixtures();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("for-two-weeks")
    public Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound() {
        return fixtureService.getFixturesByDateLeagueRound();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("league/{leagueId}")
    public Map<String, List<FixtureDTO>> getFixtures(@PathVariable("leagueId") Integer leagueId,
                                                      @RequestParam(required = false) Integer round) {
        return fixtureService.getFixtures(leagueId, round);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("team/{teamId}")
    public List<FixtureDTO> getFixtures(@PathVariable("teamId") Integer teamId,
                                        @RequestParam(required = false) String place) {
        return fixtureService.getFixtures(teamId, place);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("team/{teamId}/played/{played}")
    public List<FixtureDTO> getFixtures(@PathVariable("teamId") Integer teamId,
                                        @PathVariable("played") boolean played) {
        return fixtureService.getFixtures(teamId, played);
    }
}
