package com.mySportPage.team.rest;

import com.mySportPage.team.model.Team;
import com.mySportPage.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teams")
public class TeamRest {

    @Autowired
    private TeamService teamService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("team-id/{teamId}")
    public Team getTeam(@PathVariable("teamId") Integer teamId) {
        return teamService.getTeam(teamId);
    }
}
