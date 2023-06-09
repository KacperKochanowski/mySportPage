package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.LeagueResponse;
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
    public LeagueResponse getLeagues(@RequestParam("sportId") Integer sportId) {
        return LeagueResponse.builder()
                .withLeagues(leagueService.getLeagues(SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("country/{country}")
    public LeagueResponse getLeagues(
            @PathVariable("country") String country,
            @RequestParam("sportId") Integer sportId) {
        return LeagueResponse.builder()
                .withLeagues(country != null ?
                        leagueService.getLeagues(country, SportEnum.getById(sportId)) :
                        new ArrayList<>())
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
