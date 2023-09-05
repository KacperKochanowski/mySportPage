package com.mySportPage.task;

import com.mySportPage.cache.LeagueCoverageContainer;
import com.mySportPage.dao.LeagueCoverageDao;
import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.model.SportEnum;
import com.mySportPage.task.core.BaseTask;
import com.mySportPage.task.core.TaskList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LeagueCoverageTask extends BaseTask {

    private static final Logger log = LoggerFactory.getLogger(LeagueCoverageTask.class);

    @Autowired
    private LeagueCoverageDao leagueCoverageDao;

    @Scheduled(fixedDelay = BaseTask.HOUR)
    public void doWork() {
        process(TaskList.LEAGUE_COVERAGE_TASK);
    }

    @Override
    public void processSingleTask() {
        Map<SportEnum, List<LeagueCoverage>> coverages = leagueCoverageDao.getCoverageForAllLeagues();
        if (coverages.isEmpty()) {
            log.error("LeagueCoverageTask: no coverage found for any league! Verification required.");
        } else {
            LeagueCoverageContainer.setLeagueCoverage(coverages);
        }
    }
}
