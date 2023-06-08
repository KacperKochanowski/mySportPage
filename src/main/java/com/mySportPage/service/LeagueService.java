package com.mySportPage.service;

import com.mySportPage.model.SportEnum;
import com.mySportPage.model.dto.LeagueDTO;

import java.util.List;

public interface LeagueService {

    List<LeagueDTO> getLeagues(SportEnum sportEnum);

    List<LeagueDTO> getLeagues(String country, SportEnum sportEnum);
}
