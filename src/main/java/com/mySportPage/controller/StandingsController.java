package com.mySportPage.controller;

import com.mySportPage.model.FixturePlaceEnum;
import com.mySportPage.model.dto.StandingsDTO;
import com.mySportPage.service.StandingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StandingsController {

    private final StandingsService service;

    @Autowired
    public StandingsController(StandingsService service) {
        this.service = service;
    }

    public List<StandingsDTO> getStandings(Integer leagueId, String locationType) {
        if (locationType == null) {
            locationType = FixturePlaceEnum.ANY.getDescription();
        }
        validateParams(leagueId, locationType.toLowerCase());
        return service.getStandings(leagueId, locationType);
    }

    private static void validateParams(Integer leagueId, String locationType) throws IllegalArgumentException {
        if (leagueId == null) {
            throw new IllegalArgumentException("League id can not be null!");
        }
        if (locationType.equals(FixturePlaceEnum.ANY.getDescription())) {
            return;
        }
        for (FixturePlaceEnum place : FixturePlaceEnum.values()) {
            if (place.getDescription().equals(locationType)) {
                return;
            }
        }
        throw new IllegalArgumentException("Invalid location type!");
    }
}
