package com.mySportPage.service.impl;

import com.mySportPage.cache.LeagueCoverageContainer;
import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.model.SportEnum;
import com.mySportPage.service.LeagueCoverageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueCoverageServiceImpl implements LeagueCoverageService {

    @Override
    public List<LeagueCoverage> getCoverage(Integer sportId) {
        return LeagueCoverageContainer.getLeagueCoverage().get(SportEnum.getById(sportId));
    }

    @Override
    public List<LeagueCoverage> getCoverage(Integer sportId, Integer leagueId) {
        return LeagueCoverageContainer.getLeagueCoverage().get(SportEnum.getById(sportId))
                .stream()
                .filter(v -> v.getExternalLeagueId().equals(leagueId))
                .collect(Collectors.toList());
    }
}
