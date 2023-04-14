package com.mySportPage.league.service;

import com.mySportPage.league.dao.LeagueDao;
import com.mySportPage.league.dto.LeagueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {

    @Autowired
    private LeagueDao leagueDao;

    public List<LeagueDTO> getLeagues() {
        return leagueDao.getLeagues();
    }

    public List<LeagueDTO> getLeagues(String country) {
        return leagueDao.getLeagues(country);
    }
}
