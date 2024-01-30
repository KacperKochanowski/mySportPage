package com.mySportPage.rest;

import com.mySportPage.controller.StandingsController;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.mySportPage.rest.path.internal.CommonRestParams.LEAGUE_ID;
import static com.mySportPage.rest.path.internal.CommonRestParams.LOCATION_TYPE;
import static com.mySportPage.rest.path.internal.StandingsRestPath.GET_BY_LEAGUE_ID;
import static com.mySportPage.rest.path.internal.StandingsRestPath.ROOT_PATH;

@RestController
@RequestMapping(ROOT_PATH)
public class StandingsRestService {

    //TODO: refactor klasy względem controllera
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
    public SportPageResponse getStandings(@PathVariable(LEAGUE_ID) Integer leagueId,
                                          @RequestParam(LOCATION_TYPE) String locationType) {
        try {
            validateParams(leagueId, locationType);
            if (locationType == null) {
                locationType = "all";
            }
            return SportPageResponse.builder()
                    .withData(controller.getStandings(leagueId, locationType.toLowerCase()))
                    .withCode(HttpStatus.OK.value())
                    .withMessage(HttpStatus.OK.getReasonPhrase())
                    .build();
        } catch (Exception e) {
            return SportPageResponse.builder()
                    .withCode(HttpStatus.BAD_REQUEST.value())
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    private static void validateParams(Integer leagueId, String locationType) throws IllegalArgumentException {
        if (leagueId == null) {
            throw new IllegalArgumentException("League id can not be null!");
        }
        if (locationType == null) {
            return;
        }
        //TODO: move locations to enum
        switch (locationType.toLowerCase()) {
            case "all":
            case "home":
            case "away":
                break;
            default:
                throw new IllegalArgumentException("Invalid location type!");
        }
    }
}
