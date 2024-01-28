package com.mySportPage.rest;

import com.mySportPage.controller.TeamController;
import com.mySportPage.rest.response.TeamResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;
import static com.mySportPage.rest.path.internal.TeamRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class TeamRestService {

    @Autowired
    private TeamController controller;

    @GetMapping(GET_BY_TEAM_ID)
    public TeamResponse getTeam(
            @PathVariable(TEAM_ID) Integer teamId) {
        return TeamResponse.builder()
                .withTeams(controller.getTeam(teamId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_LEAGUE_ID)
    public TeamResponse getTeamByLeague(
            @PathVariable(LEAGUE_ID) Integer leagueId) {
        return TeamResponse.builder()
                .withTeams(controller.getTeamByLeague(leagueId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_COUNTRY_NAME)
    public TeamResponse getTeamByCountryName(
            @PathVariable(COUNTRY) String country) {
        return TeamResponse.builder()
                .withTeams(controller.getTeamByCountryName(country))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
