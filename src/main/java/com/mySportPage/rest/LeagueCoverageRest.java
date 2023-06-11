package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.LeagueCoverageResponse;
import com.mySportPage.service.LeagueCoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("coverage")
public class LeagueCoverageRest {

@Autowired
private LeagueCoverageService leagueCoverageService;

    @GetMapping
    public LeagueCoverageResponse getCoverage(@RequestParam("sportId") Integer sportId) {
        return LeagueCoverageResponse.builder()
                .withCoverage(leagueCoverageService.getCoverage().get(SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("leagueId/{leagueId}")
    public LeagueCoverageResponse getCoverage(@RequestParam("sportId") Integer sportId,
                                              @PathVariable("leagueId") Integer leagueId) {
        return LeagueCoverageResponse.builder()
                .withCoverage(leagueCoverageService.getCoverage().get(SportEnum.getById(sportId)).stream()
                        .filter(v -> v.getExternalLeagueId().equals(leagueId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}