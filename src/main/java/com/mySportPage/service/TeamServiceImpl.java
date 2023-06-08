package com.mySportPage.service;

import com.mySportPage.model.SportEnum;
import com.mySportPage.dao.TeamDao;
import com.mySportPage.model.dto.TeamDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService{

    private static final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    @Autowired
    private TeamDao teamDao;

    @Override
    public TeamDTO getTeam(Integer teamId, SportEnum sportEnum) {
        return teamDao.getTeam(teamId, sportEnum.getSchema());
    }

}
