package com.mySportPage.service;

import com.mySportPage.model.CoachCareer;
import com.mySportPage.model.SportEnum;

import java.util.List;

public interface CoachCareerService {

    List<CoachCareer> getCoachCareerById(Integer coachId, SportEnum sport);
    List<CoachCareer> getCoachCareerByName(String coachName, SportEnum sport);
}
