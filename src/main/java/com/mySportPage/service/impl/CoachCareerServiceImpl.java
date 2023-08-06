package com.mySportPage.service.impl;

import com.mySportPage.dao.CoachCareerDao;
import com.mySportPage.model.CoachCareer;
import com.mySportPage.model.SportEnum;
import com.mySportPage.service.CoachCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachCareerServiceImpl implements CoachCareerService {

    @Autowired
    private CoachCareerDao coachCareerDao;

    @Override
    public List<CoachCareer> getCoachCareerById(Integer coachId, SportEnum sport) {
        return coachCareerDao.getCoachCareerById(coachId, sport.getSchema());
    }

    @Override
    public List<CoachCareer> getCoachCareerByName(String coachName, SportEnum sport) {
        return coachCareerDao.getCoachCareerByName(coachName, sport.getSchema());
    }
}
