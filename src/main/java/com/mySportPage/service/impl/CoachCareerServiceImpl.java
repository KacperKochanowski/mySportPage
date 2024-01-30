package com.mySportPage.service.impl;

import com.mySportPage.dao.CoachCareerDao;
import com.mySportPage.model.CoachCareer;
import com.mySportPage.service.CoachCareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachCareerServiceImpl implements CoachCareerService {

    private final CoachCareerDao dao;

    @Autowired
    public CoachCareerServiceImpl(CoachCareerDao dao) {
        this.dao = dao;
    }

    @Override
    public List<CoachCareer> getCoachCareerById(Integer coachId) {
        return dao.getCoachCareerById(coachId);
    }

    @Override
    public List<CoachCareer> getCoachCareerByName(String coachName) {
        return dao.getCoachCareerByName(coachName);
    }
}
