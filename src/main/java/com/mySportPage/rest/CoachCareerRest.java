package com.mySportPage.rest;

import com.mySportPage.rest.path.CoachCareerRestPath;
import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.CoachCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CoachCareerRestPath.ROOT_PATH)
public class CoachCareerRest {

    private final CoachCareerService service;

    @Autowired
    public CoachCareerRest(CoachCareerService service) {
        this.service = service;
    }

    @GetMapping(CoachCareerRestPath.GET_CAREER_BY_COACH_ID)
    public SportPageResponse getCoachCareer(
            @PathVariable Integer coachId) {
        return SportPageResponse.builder()
                .withData(service.getCoachCareerById(coachId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(CoachCareerRestPath.GET_CAREER_BY_COACH_NAME)
    public SportPageResponse getCoachCareer(
            @PathVariable String coachName) {
        return SportPageResponse.builder()
                .withData(service.getCoachCareerByName(coachName))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
