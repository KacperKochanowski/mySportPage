package com.mySportPage.rest;

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
            @PathVariable Integer coachId) {
        return SportPageResponse.builder()
                .withData(coachCareerService.getCoachCareerById(coachId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("name/{coachName}")
    private SportPageResponse getCoachCareerByCoachName(
            @PathVariable String coachName) {
        return SportPageResponse.builder()
                .withData(coachCareerService.getCoachCareerByName(coachName))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
