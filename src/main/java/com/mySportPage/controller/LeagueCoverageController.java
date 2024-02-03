package com.mySportPage.controller;

import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.service.LeagueCoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LeagueCoverageController {

    private final LeagueCoverageService service;

    @Autowired
    public LeagueCoverageController(LeagueCoverageService service) {
        this.service = service;
    }

    public List<LeagueCoverage> getCoverage(Integer sportId) {
        return service.getCoverage(sportId);
    }

    public List<LeagueCoverage> getCoverage(Integer sportId, Integer leagueId) {
        return service.getCoverage(sportId, leagueId);
    }
}
