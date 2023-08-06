package com.mySportPage.service;

import com.mySportPage.model.dto.TeamDTO;

import java.util.List;

public interface TeamService {

    List<TeamDTO> getTeam(Integer teamId);

    List<TeamDTO> getTeamByLeague(Integer leagueId);

    List<TeamDTO> getTeamByCountryName(String countryName);
}
