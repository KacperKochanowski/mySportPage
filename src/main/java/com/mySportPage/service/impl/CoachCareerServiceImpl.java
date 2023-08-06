package com.mySportPage.service.impl;

import com.mySportPage.dao.CoachCareerDao;
import com.mySportPage.model.CoachCareer;
import com.mySportPage.service.CoachCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachCareerServiceImpl implements CoachCareerService {

    @Autowired
    private CoachCareerDao coachCareerDao;

    @Override
    public List<CoachCareer> getCoachCareerById(Integer coachId) {
        return coachCareerDao.getCoachCareerById(coachId);
    }

    @Override
    public List<CoachCareer> getCoachCareerByName(String coachName) {
        return coachCareerDao.getCoachCareerByName(coachName);
    }
}
