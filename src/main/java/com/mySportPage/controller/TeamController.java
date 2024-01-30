package com.mySportPage.controller;

import com.mySportPage.model.dto.TeamDTO;
import com.mySportPage.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TeamController {

    private final TeamService service;

    @Autowired
    public TeamController(TeamService service) {
        this.service = service;
    }

    public List<TeamDTO> getTeam(Integer teamId) {
        return service.getTeam(teamId);
    }

    public List<TeamDTO> getTeamByLeague(Integer leagueId) {
        return service.getTeamByLeague(leagueId);
    }

    public List<TeamDTO> getTeamByCountryName(String countryName) {
        return service.getTeamByCountryName(countryName);
    }
}
