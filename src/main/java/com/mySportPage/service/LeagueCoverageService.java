package com.mySportPage.service;

import com.mySportPage.model.LeagueCoverage;

import java.util.List;

public interface LeagueCoverageService {

    List<LeagueCoverage> getCoverage(Integer sportId);
    List<LeagueCoverage> getCoverage(Integer sportId, Integer leagueId);
}
