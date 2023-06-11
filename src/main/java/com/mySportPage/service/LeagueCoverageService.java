package com.mySportPage.service;

import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.model.SportEnum;

import java.util.List;
import java.util.Map;

public interface LeagueCoverageService {

    Map<SportEnum, List<LeagueCoverage>> getCoverage();
}
