package com.mySportPage.controller;

import com.mySportPage.model.Coach;
import com.mySportPage.model.request.CoachRequestModel;
import com.mySportPage.service.CoachService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Controller
public class CoachController {

    private final CoachService service;

    @Autowired
    public CoachController(CoachService service) {
        this.service = service;
    }

    public List<Coach> getCoachesByLeague(
            @Positive(message = "cannot be negative") Integer leagueId) {
        return service.getCoachesByLeague(leagueId);
    }

    public List<Coach> getCoachesByTeam(
            @Positive(message = "cannot be negative") Integer teamId) {
        return service.getCoachesByTeam(teamId);
    }

    public List<Coach> getCoachesByCountry(
            @Pattern(regexp = "^[a-zA-Z]{4,}$", message = "must contain only letters and be at least 4 characters long") String country) {
        return service.getCoachesByCountry(country);
    }

    public List<Coach> getCoaches(@Valid CoachRequestModel requestModel) {
        return service.getCoaches(requestModel);
    }
}
