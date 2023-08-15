package com.mySportPage.service;

import com.mySportPage.model.dto.StandingsDTO;

import java.util.List;

public interface StandingsService {

    List<StandingsDTO> getStandings(Integer leagueId, String locationType);
}
