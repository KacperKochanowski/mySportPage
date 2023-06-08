package com.mySportPage.service;

import com.mySportPage.model.SportEnum;
import com.mySportPage.dao.FixtureDao;
import com.mySportPage.model.dto.FixtureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FixtureService {

    @Autowired
    private FixtureDao fixtureDao;

    public List<FixtureDTO> getFixtures(SportEnum sport) {
        return fixtureDao.getFixtures(sport.getSchema());
    }

    public Map<String, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round, SportEnum sport) {
        return fixtureDao.getFixtures(leagueId, round, sport.getSchema());
    }

    public List<FixtureDTO> getFixtures(Integer teamId, String place,SportEnum sport) {
        return fixtureDao.getFixtures(teamId, place, sport.getSchema());
    }

    public List<FixtureDTO> getFixtures(Integer teamId, boolean played, SportEnum sport) {
        return fixtureDao.getFixtures(teamId, played, sport.getSchema());
    }

    public Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound(SportEnum sport) {
        return fixtureDao.getFixturesByDateLeagueRound(sport.getSchema());
    }
}
