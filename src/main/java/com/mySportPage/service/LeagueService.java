package com.mySportPage.service;

import com.mySportPage.model.dto.LeagueDTO;

import java.util.List;

public interface LeagueService {

    List<LeagueDTO> getLeagues();

    List<LeagueDTO> getLeagues(String country);
}
