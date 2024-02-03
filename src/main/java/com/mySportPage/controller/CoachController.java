package com.mySportPage.controller;

import com.mySportPage.exception.EmptyRequestBodyException;
import com.mySportPage.model.Coach;
import com.mySportPage.model.request.CoachRequestModel;
import com.mySportPage.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CoachController {

    private final CoachService service;

    @Autowired
    public CoachController(CoachService service) {
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

    public List<Coach> getCoaches(CoachRequestModel requestModel) {
        if (requestModel == null) {
            throw new EmptyRequestBodyException();
        }
        return service.getCoaches(requestModel);
    }
}
