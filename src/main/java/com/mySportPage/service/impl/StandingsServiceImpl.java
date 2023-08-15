package com.mySportPage.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mySportPage.dao.StandingsDao;
import com.mySportPage.model.dto.StandingsDTO;
import com.mySportPage.service.StandingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@Slf4j
public class StandingsServiceImpl implements StandingsService {

    @Autowired
    private StandingsDao standingsDao;

    @Override
    public List<StandingsDTO> getStandings(Integer leagueId, String locationType) {
        Object standings = standingsDao.getStandings(leagueId, locationType);
        return mapToStandingsDTO(standings);
    }

    private List<StandingsDTO> mapToStandingsDTO(Object results) {
        Gson gson = new Gson();
        Type standingsType = new TypeToken<List<StandingsDTO>>() {}.getType();
        return gson.fromJson(gson.toJson(results), standingsType);
    }
}
