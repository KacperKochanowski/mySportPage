package com.mySportPage.rest;

import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.mySportPage.rest.path.internal.CommonRestParams.COUNTRY;
import static com.mySportPage.rest.path.internal.LeagueRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class LeagueRest {

    @Autowired
    private LeagueService leagueService;

    @GetMapping(GET_ALL)
    public SportPageResponse getLeagues() {
        return SportPageResponse.builder()
                .withData(leagueService.getLeagues())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_BY_COUNTRY)
    public SportPageResponse getLeagues(
            @PathVariable(COUNTRY) String country) {
        return SportPageResponse.builder()
                .withData(country != null ?
                        leagueService.getLeagues(country) :
                        new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(ANY_PLAYS)
    public SportPageResponse anyLeaguePlaying() {
        return SportPageResponse.builder()
                .withData(leagueService.anyPlays())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
