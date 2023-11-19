package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.LeagueCoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.mySportPage.rest.path.internal.CommonRestParams.LEAGUE_ID;
import static com.mySportPage.rest.path.internal.CommonRestParams.SPORT_ID;
import static com.mySportPage.rest.path.internal.LeagueCoverageRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class LeagueCoverageRest {

    @Autowired
    private LeagueCoverageService leagueCoverageService;

    @GetMapping(GET_LEAGUE_COVERAGE_BY_SPORT)
    public SportPageResponse getCoverage(
            @PathVariable(SPORT_ID) Integer sportId) {
        return SportPageResponse.builder()
                .withData(leagueCoverageService.getCoverage().get(SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_LEAGUE_COVERAGE_BY_LEAGUE_AND_SPORT)
    public SportPageResponse getCoverage(
            @RequestParam(SPORT_ID) Integer sportId,
            @PathVariable(LEAGUE_ID) Integer leagueId) {
        return SportPageResponse.builder()
                .withData(leagueCoverageService.getCoverage().get(SportEnum.getById(sportId)).stream()
                        .filter(v -> v.getExternalLeagueId().equals(leagueId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}