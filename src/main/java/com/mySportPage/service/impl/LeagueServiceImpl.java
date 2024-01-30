package com.mySportPage.service.impl;

import com.mySportPage.dao.LeagueDao;
import com.mySportPage.model.dto.LeagueDTO;
import com.mySportPage.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueDao dao;

    @Autowired
    public LeagueServiceImpl(LeagueDao dao) {
        this.dao = dao;
    }

    @Override
    public List<LeagueDTO> getLeagues() {
        return dao.getLeagues();
    }

    @Override
    public List<LeagueDTO> getLeagues(String country) {
        return dao.getLeagues(country);
    }

    @Override
    public boolean anyPlays() {
        return dao.anyPlays();
    }
}
