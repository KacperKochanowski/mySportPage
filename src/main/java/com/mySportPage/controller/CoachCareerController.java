package com.mySportPage.controller;

import com.mySportPage.model.CoachCareer;
import com.mySportPage.service.CoachCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CoachCareerController {

    private final CoachCareerService service;

    @Autowired
    public CoachCareerController(CoachCareerService service) {
        this.service = service;
    }

    public List<CoachCareer> getCoachCareerById(Integer coachId) {
        return service.getCoachCareerById(coachId);
    }

    public List<CoachCareer> getCoachCareerByName(String coachName) {
        return service.getCoachCareerByName(coachName);
    }
}
