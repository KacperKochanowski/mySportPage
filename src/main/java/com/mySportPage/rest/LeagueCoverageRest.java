package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.path.LeagueCoverageRestPath;
import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.LeagueCoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(LeagueCoverageRestPath.ROOT_PATH)
public class LeagueCoverageRest {

    @Autowired
    private LeagueCoverageService leagueCoverageService;

    @GetMapping(LeagueCoverageRestPath.GET_LEAGUE_COVERAGE_BY_SPORT)
    public SportPageResponse getCoverage(
            @RequestParam("sportId") Integer sportId) {
        return SportPageResponse.builder()
                .withData(leagueCoverageService.getCoverage().get(SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(LeagueCoverageRestPath.GET_LEAGUE_COVERAGE_BY_LEAGUE_AND_SPORT)
    public SportPageResponse getCoverage(
            @RequestParam("sportId") Integer sportId,
            @PathVariable("leagueId") Integer leagueId) {
        return SportPageResponse.builder()
                .withData(leagueCoverageService.getCoverage().get(SportEnum.getById(sportId)).stream()
                        .filter(v -> v.getExternalLeagueId().equals(leagueId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}