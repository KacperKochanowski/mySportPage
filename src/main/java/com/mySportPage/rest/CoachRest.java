package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.CoachResponse;
import com.mySportPage.service.CoachServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("coach")
public class CoachRest {

    @Autowired
    private CoachServiceImpl coachService;

    @GetMapping("leagueId/{leagueId}")
    private CoachResponse getCoaches(
            @PathVariable Integer leagueId,
            @RequestParam("sportId") Integer sportId) {
        return CoachResponse.builder()
                .withCoaches(coachService.getCoachesByLeague(leagueId, SportEnum.getById(sportId)))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
