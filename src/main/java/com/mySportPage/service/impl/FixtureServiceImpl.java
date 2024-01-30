package com.mySportPage.service.impl;

import com.mySportPage.dao.FixtureDao;
import com.mySportPage.model.dto.FixtureDTO;
import com.mySportPage.model.request.FixtureRequestModel;
import com.mySportPage.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FixtureServiceImpl implements FixtureService {

    private final FixtureDao dao;

    @Autowired
    public FixtureServiceImpl(FixtureDao dao) {
        this.dao = dao;
    }

    @Override
    public List<FixtureDTO> getFixtures() {
        return dao.getFixtures();
    }

    @Override
    public Map<String, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round) {
        return dao.getFixtures(leagueId, round);
    }

    @Override
    public List<FixtureDTO> getFixtures(FixtureRequestModel requestModel) {
        return dao.getFixtures(requestModel);
    }

    @Override
    public Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound() {
        return dao.getFixturesByDateLeagueRound();
    }
}
