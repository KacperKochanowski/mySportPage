package com.mySportPage.service.impl;

import com.mySportPage.model.SportEnum;
import com.mySportPage.dao.TeamDao;
import com.mySportPage.model.dto.TeamDTO;
import com.mySportPage.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;

    @Override
    public List<TeamDTO> getTeam(Integer teamId, SportEnum sportEnum) {
        return teamDao.getTeam(teamId, sportEnum.getSchema());
    }

    @Override
    public List<TeamDTO> getTeamByLeague(Integer leagueId, SportEnum sportEnum) {
        return teamDao.getTeamByLeague(leagueId, sportEnum.getSchema());
    }

    @Override
    public List<TeamDTO> getTeamByCountryName(String countryName, SportEnum sportEnum) {
        return teamDao.getTeamByCountryName(countryName, sportEnum.getSchema());
    }
}
