package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.CoachCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("coach-career")
public class CoachCareerRest {

    @Autowired
    private CoachCareerService coachCareerService;

    @GetMapping("id/{coachId}")
    private SportPageResponse getCoachCareerById(
            @PathVariable Integer coachId,
            @RequestParam("sportId") Integer sportId) {
        return SportPageResponse.builder()
                .withData(coachCareerService.getCoachCareerById(coachId, SportEnum.FOOTBALL))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("name/{coachName}")
    private SportPageResponse getCoachCareerByCoachName(
            @PathVariable String coachName,
            @RequestParam("sportId") Integer sportId) {
        return SportPageResponse.builder()
                .withData(coachCareerService.getCoachCareerByName(coachName, SportEnum.FOOTBALL))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
