package com.mySportPage.rest;

import com.mySportPage.controller.LeagueController;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.mySportPage.rest.path.internal.CommonRestParams.COUNTRY;
import static com.mySportPage.rest.path.internal.LeagueRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class LeagueRestService {

    @Autowired
    private LeagueController controller;

    @GetMapping(GET_ALL)
    public SportPageResponse getLeagues() {
        return SportPageResponse.builder()
                .withData(controller.getLeagues())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    //TODO: logika do controllera
    @GetMapping(GET_BY_COUNTRY)
    public SportPageResponse getLeagues(
            @PathVariable(COUNTRY) String country) {
        return SportPageResponse.builder()
                .withData(country != null ?
                        controller.getLeagues(country) :
                        new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(ANY_PLAYS)
    public SportPageResponse anyLeaguePlaying() {
        return SportPageResponse.builder()
                .withData(controller.anyPlays())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
