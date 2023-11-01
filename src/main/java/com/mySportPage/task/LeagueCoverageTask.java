package com.mySportPage.task;

import com.mySportPage.cache.LeagueCoverageContainer;
import com.mySportPage.dao.LeagueCoverageDao;
import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.model.SportEnum;
import com.mySportPage.task.core.BaseTask;
import com.mySportPage.task.core.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Profile("production")
public class LeagueCoverageTask extends BaseTask {

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
