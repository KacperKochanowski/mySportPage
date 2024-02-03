package com.mySportPage.rest;

import com.mySportPage.controller.CoachCareerController;
import com.mySportPage.model.CoachCareer;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mySportPage.rest.path.internal.CoachCareerRestPath.*;
import static com.mySportPage.rest.path.internal.CommonRestParams.COACH_ID;
import static com.mySportPage.rest.path.internal.CommonRestParams.COACH_NAME;

@RestController
@RequestMapping(ROOT_PATH)
public class CoachCareerRestService extends AbstractRestService {

    private final CoachCareerController controller;

    @Autowired
    public CoachCareerRestService(CoachCareerController controller) {
        this.controller = controller;
    }

    @GetMapping(GET_CAREER_BY_COACH_ID)
    public SportPageResponse<List<CoachCareer>> getCoachCareer(
            @PathVariable(COACH_ID) Integer coachId) {
        return processResponse(() -> controller.getCoachCareerById(coachId));
    }

    @GetMapping(GET_CAREER_BY_COACH_NAME)
    public SportPageResponse<List<CoachCareer>> getCoachCareer(
            @PathVariable(COACH_NAME) String coachName) {
        return processResponse(() -> controller.getCoachCareerByName(coachName));
    }
}
