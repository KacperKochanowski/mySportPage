package com.mySportPage.league.service;

import com.mySportPage.dataAcquisition.model.SportEnum;
import com.mySportPage.league.dao.LeagueDao;
import com.mySportPage.league.dto.LeagueDTO;
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
