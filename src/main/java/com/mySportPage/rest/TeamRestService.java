package com.mySportPage.rest;

import com.mySportPage.controller.TeamController;
import com.mySportPage.model.dto.TeamDTO;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;
import static com.mySportPage.rest.path.internal.TeamRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class TeamRestService extends AbstractRestService {

    private final TeamController controller;

    @Autowired
    public TeamRestService(TeamController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_BY_TEAM_ID)
    public SportPageResponse<List<TeamDTO>> getTeam(
            @PathVariable(TEAM_ID) Integer teamId) {
        return processResponse(() -> controller.getTeam(teamId));
    }

    @GetMapping(GET_BY_LEAGUE_ID)
    public SportPageResponse<List<TeamDTO>> getTeamByLeague(
            @PathVariable(LEAGUE_ID) Integer leagueId) {
        return processResponse(() -> controller.getTeamByLeague(leagueId));
    }

    @GetMapping(GET_BY_COUNTRY_NAME)
    public SportPageResponse<List<TeamDTO>> getTeamByCountryName(
            @PathVariable(COUNTRY) String country) {
        return processResponse(() -> controller.getTeamByCountryName(country));
    }
}
