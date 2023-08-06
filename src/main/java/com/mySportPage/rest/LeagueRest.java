package com.mySportPage.rest;

import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("leagues")
public class LeagueRest {

    @Autowired
    private LeagueService leagueService;

    @GetMapping("all")
    public SportPageResponse getLeagues() {
        return SportPageResponse.builder()
                .withData(leagueService.getLeagues())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("country/{country}")
    public SportPageResponse getLeagues(
            @PathVariable("country") String country) {
        return SportPageResponse.builder()
                .withData(country != null ?
                        leagueService.getLeagues(country) :
                        new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
