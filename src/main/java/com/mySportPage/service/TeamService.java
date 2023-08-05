package com.mySportPage.service;

import com.mySportPage.model.SportEnum;
import com.mySportPage.model.dto.TeamDTO;

import java.util.List;

public interface TeamService {

    List<TeamDTO> getTeam(Integer teamId, SportEnum sportEnum);

    List<TeamDTO> getTeamByLeague(Integer leagueId, SportEnum sportEnum);

    List<TeamDTO> getTeamByCountryName(String countryName, SportEnum sportEnum);
}
