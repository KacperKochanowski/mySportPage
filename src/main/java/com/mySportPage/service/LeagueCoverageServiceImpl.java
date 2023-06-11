package com.mySportPage.service;

import com.mySportPage.cache.LeagueCoverageContainer;
import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.model.SportEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LeagueCoverageServiceImpl implements LeagueCoverageService{

    @Override
    public Map<SportEnum, List<LeagueCoverage>> getCoverage() {
        return LeagueCoverageContainer.getLeagueCoverage();
    }
}
