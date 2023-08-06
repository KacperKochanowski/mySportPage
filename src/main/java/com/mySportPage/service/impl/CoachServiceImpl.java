package com.mySportPage.service.impl;

import com.mySportPage.dao.CoachDao;
import com.mySportPage.model.Coach;
import com.mySportPage.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachDao coachDao;

    @Override
    public List<Coach> getCoachesByLeague(Integer leagueId) {
        return coachDao.getCoachesByLeague(leagueId);
    }

    @Override
    public List<Coach> getCoachesByTeam(Integer teamId) {
        return coachDao.getCoachesByTeam(teamId);
    }

    @Override
    public List<Coach> getCoachesByCountry(String countryCode) {
        return coachDao.getCoachesByCountryCode(countryCode);
    }

    @Override
    public List<Coach> getCoaches(Map<String, Object> params) {
        return coachDao.getCoaches(params);
    }
}
