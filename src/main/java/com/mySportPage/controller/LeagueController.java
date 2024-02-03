package com.mySportPage.controller;

import com.mySportPage.model.dto.LeagueDTO;
import com.mySportPage.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LeagueController {

    private final LeagueService service;

    @Autowired
    public LeagueController(LeagueService service) {
        this.service = service;
    }

    public List<LeagueDTO> getLeagues() {
        return service.getLeagues();
    }

    public List<LeagueDTO> getLeagues(String country) {
        return country != null ? service.getLeagues(country) : new ArrayList<>();
    }

    public boolean anyPlays() {
        return service.anyPlays();
    }
}
