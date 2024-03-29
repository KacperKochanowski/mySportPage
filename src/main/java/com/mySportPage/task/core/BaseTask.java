package com.mySportPage.task.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class BaseTask {

    protected static final Logger log = LoggerFactory.getLogger(BaseTask.class);

    @Value("${setting.IS_FIXTURE_UPDATER_TASK_ENABLED:false}")
    private boolean isFixtureUpdaterEnabled;

    @Value("${setting.IS_LEAGUE_COVERAGE_TASK_ENABLED:false}")
    private boolean isLeagueCoverageEnabled;

    public abstract void processSingleTask();

    public void process(TaskDetails task) {
        String taskName = task.getTaskName();
        if (!isEnabled(task.getTaskName())) {
            log.debug("{} is currently disabled.", taskName);
        } else {
            log.info(">>> STARTED: {} <<<", taskName);
            processSingleTask();
            log.info(">>> FINISHED: {} <<<", taskName);
        }
    }

    public boolean isEnabled(String taskName) {
        switch (taskName) {
            case "FixtureUpdaterTask" -> {
                return isFixtureUpdaterEnabled;
            }
            case "LeagueCoverageTask" -> {
                return isLeagueCoverageEnabled;
            }
            default -> {
                return true;
            }
        }
    }
}
