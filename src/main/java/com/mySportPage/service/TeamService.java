package com.mySportPage.service;

import com.mySportPage.model.SportEnum;
import com.mySportPage.model.dto.TeamDTO;

public interface TeamService {

    TeamDTO getTeam(Integer teamId, SportEnum sportEnum);
}
