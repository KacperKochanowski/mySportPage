package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.TeamResponse;
import com.mySportPage.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teams")
public class TeamRest {

    @Autowired
    private TeamService teamService;

    @GetMapping("team-id/{teamId}")
    public TeamResponse getTeam(
            @PathVariable("teamId") Integer teamId,
            @RequestParam("sportId") Integer sportId) {
        return TeamResponse.builder()
                .withTeams(teamService.getTeam(teamId, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
