package com.mySportPage.service;

import com.mySportPage.model.CoachCareer;

import java.util.List;

public interface CoachCareerService {

    List<CoachCareer> getCoachCareerById(Integer coachId);
    List<CoachCareer> getCoachCareerByName(String coachName);
}
