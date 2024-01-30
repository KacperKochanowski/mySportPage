package com.mySportPage.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mySportPage.dao.StandingsDao;
import com.mySportPage.model.dto.StandingsDTO;
import com.mySportPage.service.StandingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class StandingsServiceImpl implements StandingsService {

    private final StandingsDao dao;

    @Autowired
    public StandingsServiceImpl(StandingsDao dao) {
        this.dao = dao;
    }

    @Override
    public List<StandingsDTO> getStandings(Integer leagueId, String locationType) {
        Object standings = dao.getStandings(leagueId, locationType);
        return mapToStandingsDTO(standings);
    }

    private List<StandingsDTO> mapToStandingsDTO(Object results) {
        Gson gson = new Gson();
        Type standingsType = new TypeToken<List<StandingsDTO>>() {
        }.getType();
        return gson.fromJson(gson.toJson(results), standingsType);
    }
}
