package com.mySportPage.task;

import com.mySportPage.cache.LeagueCoverageContainer;
import com.mySportPage.dao.LeagueCoverageDao;
import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.model.SportEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LeagueCoverageTask {

    private static final Logger log = LoggerFactory.getLogger(LeagueCoverageTask.class);

    @Autowired
    private LeagueCoverageDao leagueCoverageDao;

    @Scheduled(fixedDelay = 3_600_000L)
    private void setLeagueCoverage() {
        log.info("STARTED LeagueCoverageTask");
        Map<SportEnum, List<LeagueCoverage>> coverages = leagueCoverageDao.getCoverageForAllLeagues();
        LeagueCoverageContainer.setLeagueCoverage(coverages);
        log.info("FINISHED LeagueCoverageTask");
    }
}
