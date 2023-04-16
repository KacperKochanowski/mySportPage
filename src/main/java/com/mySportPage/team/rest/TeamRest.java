package com.mySportPage.team.rest;

import com.mySportPage.dataAcquisition.model.SportEnum;
import com.mySportPage.team.dto.TeamDTO;
import com.mySportPage.team.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teams")
public class TeamRest {

    @Autowired
    private TeamService teamService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("team-id/{teamId}")
    public TeamDTO getTeam(
            @PathVariable("teamId") Integer teamId,
            @RequestParam("sportId") Integer sportId) {
        return teamService.getTeam(teamId, SportEnum.getById(sportId));
    }
}
