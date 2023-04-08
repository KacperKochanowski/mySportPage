package com.mySportPage.dataAcquisition.service;

import com.mySportPage.dataAcquisition.dao.FixtureDao;
import com.mySportPage.dataAcquisition.model.FixtureDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FixtureService {

    private static final Logger log = LoggerFactory.getLogger(FixtureService.class);

    @Autowired
    private FixtureDao fixtureDao;

    public List<FixtureDTO> getFixtures() {
        return fixtureDao.getFixtures();
    }

    public Map<Integer, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round) {
        return fixtureDao.getFixtures(leagueId, round);
    }
}
