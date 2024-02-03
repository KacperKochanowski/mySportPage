package com.mySportPage.controller;

import com.mySportPage.model.dto.StadiumDTO;
import com.mySportPage.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StadiumController {

    private final StadiumService service;

    @Autowired
    public StadiumController(StadiumService service) {
        this.service = service;
    }

    public List<StadiumDTO> getByCity(String city) {
        return city != null ? service.getByCity(city) : new ArrayList<>();
    }

    public List<StadiumDTO> getByAddress(String address) {
        return address != null ? service.getByAddress(address) : new ArrayList<>();
    }

    public List<StadiumDTO> getByTeamId(Integer teamId) {
        return teamId != null ? service.getByTeamId(teamId) : new ArrayList<>();
    }

    public List<StadiumDTO> getByTeamName(String teamName) {
        return teamName != null ? service.getByTeamName(teamName) : new ArrayList<>();
    }

    public List<StadiumDTO> getByName(String name) {
        return name != null ? service.getByName(name) : new ArrayList<>();
    }
}
