package com.mySportPage.dataAcquisition.rest;

import com.mySportPage.dataAcquisition.model.FixtureDTO;
import com.mySportPage.dataAcquisition.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("fixtures")
public class FixtureRest {

    @Autowired
    FixtureService fixtureService;

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("current")
    public List<FixtureDTO> getFixtures() {
        return fixtureService.getFixtures();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("{leagueId}")
    public Map<Integer, List<FixtureDTO>> getFixtures(@PathVariable("leagueId") Integer leagueId,
                                                      @RequestParam(required = false) Integer round) {
        return fixtureService.getFixtures(leagueId, round);
    }
}
