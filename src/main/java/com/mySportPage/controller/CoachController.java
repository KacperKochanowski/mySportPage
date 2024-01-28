package com.mySportPage.controller;

import com.mySportPage.model.Coach;
import com.mySportPage.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class CoachController {

    private final CoachService service;

    @Autowired
    private CoachController(CoachService service) {
        this.service = service;
    }

    public List<Coach> getCoachesByLeague(Integer leagueId) {
        return service.getCoachesByLeague(leagueId);
    }

    public List<Coach> getCoachesByTeam(Integer teamId) {
        return service.getCoachesByTeam(teamId);
    }

    public List<Coach> getCoachesByCountry(String country) {
        return service.getCoachesByCountry(country);
    }

    public List<Coach> getCoaches(Map<String, Object> params) {
        return service.getCoaches(params);
    }
}
