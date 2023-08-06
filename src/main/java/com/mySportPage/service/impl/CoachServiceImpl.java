package com.mySportPage.service.impl;

import com.mySportPage.dao.CoachDao;
import com.mySportPage.model.Coach;
import com.mySportPage.model.SportEnum;
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
    public List<Coach> getCoachesByLeague(Integer leagueId, SportEnum sport) {
        return coachDao.getCoachesByLeague(leagueId, sport.getSchema());
    }

    @Override
    public List<Coach> getCoachesByTeam(Integer teamId, SportEnum sport) {
        return coachDao.getCoachesByTeam(teamId, sport.getSchema());
    }

    @Override
    public List<Coach> getCoachesByCountry(String countryCode, SportEnum sport) {
        return coachDao.getCoachesByCountryCode(countryCode, sport.getSchema());
    }

    @Override
    public List<Coach> getCoaches(Map<String, Object> params, SportEnum sportEnum) {
        return coachDao.getCoaches(params, sportEnum.getSchema());
    }
}
