package com.mySportPage.controller;

import com.mySportPage.model.dto.TeamDTO;
import com.mySportPage.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TeamController {

    private final TeamService service;

    @Autowired
    public TeamController(TeamService service) {
        this.service = service;
    }

    public List<TeamDTO> getTeam(Integer teamId) {
        return teamId != null ? service.getTeam(teamId) : new ArrayList<>();
    }

    public List<TeamDTO> getTeamByLeague(Integer leagueId) {
        return leagueId != null ? service.getTeamByLeague(leagueId) : new ArrayList<>();
    }

    public List<TeamDTO> getTeamByCountryName(String countryName) {
        return countryName != null ? service.getTeamByCountryName(countryName) : new ArrayList<>();
    }
}
