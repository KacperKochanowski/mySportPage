package com.mySportPage.cache;

import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.model.SportEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueCoverageContainer {

    private static Map<SportEnum, List<LeagueCoverage>> leagueCoverage = new HashMap<>();

    public static Map<SportEnum, List<LeagueCoverage>> getLeagueCoverage() {
        return leagueCoverage;
    }

    public synchronized static void setLeagueCoverage(Map<SportEnum, List<LeagueCoverage>> leagueCoverage) {
        LeagueCoverageContainer.leagueCoverage = leagueCoverage;
    }
}
