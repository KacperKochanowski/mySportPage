package com.mySportPage.service;

import com.mySportPage.dao.CoachDao;
import com.mySportPage.model.Coach;
import com.mySportPage.model.SportEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachServiceImpl implements CoachService{

    @Autowired
    private CoachDao coachDao;

    @Override
    public List<Coach> getCoachesByLeague(Integer leagueId, SportEnum sport) {
        return coachDao.getCoachesByLeague(leagueId, sport.getSchema());
    }
}
