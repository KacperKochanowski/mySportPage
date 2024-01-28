package com.mySportPage.controller;

import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.model.SportEnum;
import com.mySportPage.service.LeagueCoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class LeagueCoverageController {

    private final LeagueCoverageService service;

    @Autowired
    private LeagueCoverageController(LeagueCoverageService service) {
        this.service = service;
    }

    public Map<SportEnum, List<LeagueCoverage>> getCoverage() {
        return service.getCoverage();
    }
}
