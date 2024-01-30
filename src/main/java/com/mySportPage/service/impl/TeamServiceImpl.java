package com.mySportPage.service.impl;

import com.mySportPage.dao.TeamDao;
import com.mySportPage.mapper.mapStruct.TeamMapper;
import com.mySportPage.model.Team;
import com.mySportPage.model.dto.TeamDTO;
import com.mySportPage.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamDao dao;

    @Autowired
    public TeamServiceImpl(TeamDao dao) {
        this.dao = dao;
    }

    @Autowired
    public TeamMapper teamMapper;

    @Override
    public List<TeamDTO> getTeam(Integer teamId) {
        List<Team> teams = dao.getTeam(teamId);
        return teamMapper.mapToDTO(teams);
    }

    @Override
    public List<TeamDTO> getTeamByLeague(Integer leagueId) {
        List<Team> teams = dao.getTeamByLeague(leagueId);
        return teamMapper.mapToDTO(teams);
    }

    @Override
    public List<TeamDTO> getTeamByCountryName(String countryName) {
        List<Team> teams = dao.getTeamByCountryName(countryName);
        return teamMapper.mapToDTO(teams);
    }
}
