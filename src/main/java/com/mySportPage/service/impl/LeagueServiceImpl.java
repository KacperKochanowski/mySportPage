package com.mySportPage.service.impl;

import com.mySportPage.dao.LeagueDao;
import com.mySportPage.model.dto.LeagueDTO;
import com.mySportPage.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueServiceImpl implements LeagueService {

    @Autowired
    private LeagueDao leagueDao;

    @Override
    public List<LeagueDTO> getLeagues() {
        return leagueDao.getLeagues();
    }

    @Override
    public List<LeagueDTO> getLeagues(String country) {
        return leagueDao.getLeagues(country);
    }

    @Override
    public boolean anyPlays() {
        return leagueDao.anyPlays();
    }
}
