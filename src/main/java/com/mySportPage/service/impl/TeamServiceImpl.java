package com.mySportPage.service.impl;

import com.mySportPage.dao.TeamDao;
import com.mySportPage.mapper.mapStruct.TeamMapper;
import com.mySportPage.model.Team;
import com.mySportPage.model.dto.TeamDTO;
import com.mySportPage.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;

    @Autowired
    public TeamMapper teamMapper;

    @Override
    public List<TeamDTO> getTeam(Integer teamId) {
        List<Team> teams = teamDao.getTeam(teamId);
        log.info("" + teamMapper.mapToDTO(teams.get(0)));
        return teamMapper.mapToDTO(teams);
    }

    @Override
    public List<TeamDTO> getTeamByLeague(Integer leagueId) {
        List<Team> teams = teamDao.getTeamByLeague(leagueId);
        return teamMapper.mapToDTO(teams);
    }

    @Override
    public List<TeamDTO> getTeamByCountryName(String countryName) {
        List<Team> teams = teamDao.getTeamByCountryName(countryName);
        return teamMapper.mapToDTO(teams);
    }
}
