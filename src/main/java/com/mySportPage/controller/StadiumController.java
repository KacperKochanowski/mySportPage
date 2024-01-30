package com.mySportPage.controller;

import com.mySportPage.model.dto.StadiumDTO;
import com.mySportPage.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class StadiumController {

    private final StadiumService service;

    @Autowired
    public StadiumController(StadiumService service) {
        this.service = service;
    }

    public List<StadiumDTO> getByCity(String city) {
        return service.getByCity(city);
    }

    public List<StadiumDTO> getByAddress(String address) {
        return service.getByAddress(address);
    }

    public List<StadiumDTO> getByTeamId(Integer teamId) {
        return service.getByTeamId(teamId);
    }

    public List<StadiumDTO> getByTeamName(String teamName) {
        return service.getByTeamName(teamName);
    }

    public List<StadiumDTO> getByName(String name) {
        return service.getByName(name);
    }
}
