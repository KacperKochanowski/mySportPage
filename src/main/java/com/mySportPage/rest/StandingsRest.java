package com.mySportPage.rest;

import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.StandingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.mySportPage.rest.path.internal.StandingsRestPath.GET_BY_LEAGUE_ID;
import static com.mySportPage.rest.path.internal.StandingsRestPath.ROOT_PATH;

@RestController
@RequestMapping(ROOT_PATH)
public class StandingsRest {

    @Autowired
    private StandingsService standingsService;

    /**
     * Data returned from request below may be incorrect sometimes.
     * In this case, the fixtures and standings entities need to be updated.
     */

    @GetMapping(GET_BY_LEAGUE_ID)
    public SportPageResponse getStandings(@PathVariable Integer leagueId,
                                          @RequestParam String locationType) {
        try {
            validateParams(leagueId, locationType);
            if (locationType == null) {
                locationType = "all";
            }
            return SportPageResponse.builder()
                    .withData(standingsService.getStandings(leagueId, locationType.toLowerCase()))
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
