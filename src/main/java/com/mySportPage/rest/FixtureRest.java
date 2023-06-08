package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.model.dto.FixtureDTO;
import com.mySportPage.service.FixtureService;
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
    public List<FixtureDTO> getFixtures(
            @RequestParam("sportId") Integer sportId) {
        return fixtureService.getFixtures(SportEnum.getById(sportId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("for-two-weeks")
    public Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound(
            @RequestParam("sportId") Integer sportId) {
        return fixtureService.getFixturesByDateLeagueRound(SportEnum.getById(sportId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("league/{leagueId}")
    public Map<String, List<FixtureDTO>> getFixtures(
            @PathVariable("leagueId") Integer leagueId,
            @RequestParam(required = false) Integer round,
            @RequestParam("sportId") Integer sportId) {
        return fixtureService.getFixtures(leagueId, round, SportEnum.getById(sportId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("team/{teamId}")
    public List<FixtureDTO> getFixtures(
            @PathVariable("teamId") Integer teamId,
            @RequestParam(required = false) String place,
            @RequestParam("sportId") Integer sportId) {
        return fixtureService.getFixtures(teamId, place, SportEnum.getById(sportId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("team/{teamId}/played/{played}")
    public List<FixtureDTO> getFixtures(
            @PathVariable("teamId") Integer teamId,
            @PathVariable("played") boolean played,
            @RequestParam("sportId") Integer sportId) {
        return fixtureService.getFixtures(teamId, played, SportEnum.getById(sportId));
    }
}
