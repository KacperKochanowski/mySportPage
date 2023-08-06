package com.mySportPage.service.impl;

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
    public List<TeamDTO> getTeam(Integer teamId) {
        return teamDao.getTeam(teamId);
    }

    @Override
    public List<TeamDTO> getTeamByLeague(Integer leagueId) {
        return teamDao.getTeamByLeague(leagueId);
    }

    @Override
    public List<TeamDTO> getTeamByCountryName(String countryName) {
        return teamDao.getTeamByCountryName(countryName);
    }
}
