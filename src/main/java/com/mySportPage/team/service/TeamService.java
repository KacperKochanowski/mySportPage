package com.mySportPage.team.service;

import com.mySportPage.fixture.service.FixtureService;
import com.mySportPage.team.dao.TeamDao;
import com.mySportPage.team.dto.TeamDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    private static final Logger log = LoggerFactory.getLogger(FixtureService.class);

    @Autowired
    private TeamDao teamDao;

    public TeamDTO getTeam(Integer teamId) {
        return teamDao.getTeam(teamId);
    }
}
