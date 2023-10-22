package com.mySportPage.rest;

import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.CoachCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("coach-career")
public class CoachCareerRest {

    private final CoachCareerService service;

    @Autowired
    public CoachCareerRest(CoachCareerService service) {
        this.service = service;
    }

    @GetMapping("id/{coachId}")
    public SportPageResponse getCoachCareer(
            @PathVariable Integer coachId) {
        return SportPageResponse.builder()
                .withData(service.getCoachCareerById(coachId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping("name/{coachName}")
    public SportPageResponse getCoachCareer(
            @PathVariable String coachName) {
        return SportPageResponse.builder()
                .withData(service.getCoachCareerByName(coachName))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
