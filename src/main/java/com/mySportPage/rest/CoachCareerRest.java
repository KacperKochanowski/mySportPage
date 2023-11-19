package com.mySportPage.rest;

import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.CoachCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.mySportPage.rest.path.internal.CoachCareerRestPath.*;
import static com.mySportPage.rest.path.internal.CommonRestParams.COACH_ID;
import static com.mySportPage.rest.path.internal.CommonRestParams.COACH_NAME;

@RestController
@RequestMapping(ROOT_PATH)
public class CoachCareerRest {

    private final CoachCareerService service;

    @Autowired
    public CoachCareerRest(CoachCareerService service) {
        this.service = service;
    }

    @GetMapping(GET_CAREER_BY_COACH_ID)
    public SportPageResponse getCoachCareer(
            @PathVariable(COACH_ID) Integer coachId) {
        return SportPageResponse.builder()
                .withData(service.getCoachCareerById(coachId))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    @GetMapping(GET_CAREER_BY_COACH_NAME)
    public SportPageResponse getCoachCareer(
            @PathVariable(COACH_NAME) String coachName) {
        return SportPageResponse.builder()
                .withData(service.getCoachCareerByName(coachName))
                .withCode(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.getReasonPhrase())
                .build();
    }
}
