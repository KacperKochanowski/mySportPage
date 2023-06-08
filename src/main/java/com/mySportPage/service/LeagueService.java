package com.mySportPage.service;

import com.mySportPage.model.SportEnum;
import com.mySportPage.dao.LeagueDao;
import com.mySportPage.model.dto.LeagueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {

    @Autowired
    private LeagueDao leagueDao;

    public List<LeagueDTO> getLeagues(SportEnum sportEnum) {
        return leagueDao.getLeagues(sportEnum.getSchema());
    }

    public List<LeagueDTO> getLeagues(String country, SportEnum sportEnum) {
        return leagueDao.getLeagues(country, sportEnum.getSchema());
    }
}
