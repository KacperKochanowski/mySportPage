package com.mySportPage.rest;

import com.mySportPage.rest.response.TeamResponse;
import com.mySportPage.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.mySportPage.rest.path.internal.TeamRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class TeamRest {

    @Autowired
    private TeamService teamService;

    @GetMapping(GET_BY_TEAM_ID)
    public TeamResponse getTeam(
            @PathVariable("teamId") Integer teamId) {
        return TeamResponse.builder()
                .withTeams(teamService.getTeam(teamId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_LEAGUE_ID)
    public TeamResponse getTeamByLeague(
            @PathVariable("leagueId") Integer leagueId) {
        return TeamResponse.builder()
                .withTeams(teamService.getTeamByLeague(leagueId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_COUNTRY_NAME)
    public TeamResponse getTeamByCountryName(
            @PathVariable("countryName") String countryName) {
        return TeamResponse.builder()
                .withTeams(teamService.getTeamByCountryName(countryName))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
