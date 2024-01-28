package com.mySportPage.controller;

import com.mySportPage.model.dto.FixtureDTO;
import com.mySportPage.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class FixtureController {

    private final FixtureService service;

    @Autowired
    private FixtureController(FixtureService service) {
        this.service = service;
    }

    public List<FixtureDTO> getFixtures() {
        return service.getFixtures();
    }

    public Map<String, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round) {
        return service.getFixtures(leagueId, round);
    }

    public List<FixtureDTO> getFixtures(Integer teamId, String place) {
        return service.getFixtures(teamId, place);
    }

    public List<FixtureDTO> getFixtures(Integer teamId, boolean played) {
        return service.getFixtures(teamId, played);
    }

    public Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound() {
        return service.getFixturesByDateLeagueRound();
    }
}
