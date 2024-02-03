package com.mySportPage.rest;

import com.mySportPage.controller.StandingsController;
import com.mySportPage.model.dto.StandingsDTO;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mySportPage.rest.path.internal.CommonRestParams.LEAGUE_ID;
import static com.mySportPage.rest.path.internal.CommonRestParams.LOCATION_TYPE;
import static com.mySportPage.rest.path.internal.StandingsRestPath.GET_BY_LEAGUE_ID;
import static com.mySportPage.rest.path.internal.StandingsRestPath.ROOT_PATH;

@RestController
@RequestMapping(ROOT_PATH)
public class StandingsRestService extends AbstractRestService {

    private final StandingsController controller;

    @Autowired
    public StandingsRestService(StandingsController controller) {
        this.controller = controller;
    }

    /**
     * Data returned from request below may be incorrect sometimes.
     * In this case, the fixtures and standings entities need to be updated.
     */

    @GetMapping(GET_BY_LEAGUE_ID)
    public SportPageResponse<List<StandingsDTO>> getStandings(
            @PathVariable(LEAGUE_ID) Integer leagueId,
            @RequestParam(LOCATION_TYPE) String locationType) {
        return processResponse(() -> controller.getStandings(leagueId, locationType));
    }
}
