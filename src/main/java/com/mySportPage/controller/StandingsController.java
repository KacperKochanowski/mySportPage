package com.mySportPage.controller;

import com.mySportPage.model.dto.StandingsDTO;
import com.mySportPage.service.StandingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StandingsController {

    private final StandingsService service;

    @Autowired
    private StandingsController(StandingsService service) {
        this.service = service;
    }

    public List<StandingsDTO> getStandings(Integer leagueId, String locationType) {
        return service.getStandings(leagueId, locationType);
    }
}
