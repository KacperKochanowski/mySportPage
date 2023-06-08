package com.mySportPage.rest;

import com.mySportPage.model.SportEnum;
import com.mySportPage.model.dto.LeagueDTO;
import com.mySportPage.service.LeagueService;
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
    public List<LeagueDTO> getLeagues(@RequestParam("sportId") Integer sportId) {
        return leagueService.getLeagues(SportEnum.getById(sportId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("country/{country}")
    public List<LeagueDTO> getLeagues(
            @PathVariable("country") String country,
            @RequestParam("sportId") Integer sportId) {
        return country != null ?
                leagueService.getLeagues(country, SportEnum.getById(sportId)):
                new ArrayList<>();
    }
}
