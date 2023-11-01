package com.mySportPage.task;

import com.mySportPage.dao.FixtureDao;
import com.mySportPage.task.core.BaseTask;
import com.mySportPage.task.core.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class MissingResultingTask extends BaseTask {

    @Autowired
    private FixtureDao fixtureDao;

    @Scheduled(fixedDelay = BaseTask.HOUR, initialDelay = 15 * BaseTask.SECOND)
    public void doWork() {
        process(TaskList.MISSING_RESULTING_TASK);
    }

    @Override
    public void processSingleTask() {
        Integer issues = fixtureDao.checkForMissingResulting();
        if (issues > 0) {
            log.warn("MissingResultingTask: found {} not settled results!", issues);
        }
    }
}
