package com.mySportPage.controller;

import com.mySportPage.model.dto.FixtureDTO;
import com.mySportPage.model.request.FixtureRequestModel;
import com.mySportPage.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class FixtureController {

    private final FixtureService service;

    @Autowired
    public FixtureController(FixtureService service) {
        this.service = service;
    }

    public List<FixtureDTO> getFixtures() {
        return service.getFixtures();
    }

    public Map<String, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round) {
        return service.getFixtures(leagueId, round);
    }

    //TODO:wyjątek, ze teamId jest obowiąkowy
    public List<FixtureDTO> getFixtures(FixtureRequestModel requestModel) {
        if(requestModel == null || requestModel.getTeamId() == null) {
            return new ArrayList<>();
        }
        return service.getFixtures(requestModel);
    }

    public Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound() {
        return service.getFixturesByDateLeagueRound();
    }
}
