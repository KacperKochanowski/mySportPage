package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.model.dto.TeamDTO;
import com.mySportPage.service.TeamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("teams")
public class TeamRest {

    @Autowired
    private TeamServiceImpl teamServiceImpl;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("team-id/{teamId}")
    public TeamDTO getTeam(
            @PathVariable("teamId") Integer teamId,
            @RequestParam("sportId") Integer sportId) {
        return teamServiceImpl.getTeam(teamId, SportEnum.getById(sportId));
    }
}
