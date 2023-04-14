package com.mySportPage.league.rest;

import com.mySportPage.league.dto.LeagueDTO;
import com.mySportPage.league.service.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("leagues")
public class LeagueRest {

    @Autowired
    private LeagueService leagueService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("all")
    public List<LeagueDTO> getLeagues() {
        return leagueService.getLeagues();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("country/{country}")
    public List<LeagueDTO> getLeagues(@PathVariable("country") String country) {
        return country != null ?
                leagueService.getLeagues(country):
                new ArrayList<>();
    }
}
