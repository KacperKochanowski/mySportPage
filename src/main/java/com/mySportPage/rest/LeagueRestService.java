package com.mySportPage.rest;

import com.mySportPage.controller.LeagueController;
import com.mySportPage.model.dto.LeagueDTO;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mySportPage.rest.path.internal.CommonRestParams.COUNTRY;
import static com.mySportPage.rest.path.internal.LeagueRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class LeagueRestService extends AbstractRestService {

    private final LeagueController controller;

    @Autowired
    public LeagueRestService(LeagueController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_ALL)
    public SportPageResponse<List<LeagueDTO>> getLeagues() {
        return processResponse(controller::getLeagues);
    }

    @GetMapping(GET_BY_COUNTRY)
    public SportPageResponse<List<LeagueDTO>> getLeagues(
            @PathVariable(COUNTRY) String country) {
        return processResponse(() -> controller.getLeagues(country));
    }

    @GetMapping(ANY_PLAYS)
    public SportPageResponse<Boolean> anyLeaguePlaying() {
        return processResponse(controller::anyPlays);
    }
}
