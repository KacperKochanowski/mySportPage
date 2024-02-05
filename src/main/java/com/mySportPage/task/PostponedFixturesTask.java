package com.mySportPage.task;

import com.mySportPage.dao.FixtureDao;
import com.mySportPage.task.core.BaseTask;
import com.mySportPage.task.core.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.mySportPage.comonTools.TimeUnits.HOUR;
import static com.mySportPage.comonTools.TimeUnits.SECOND;

@Component
@Profile("production")
public class PostponedFixturesTask extends BaseTask {

    @Autowired
    private FixtureDao fixtureDao;

    @Scheduled(fixedDelay = 12 * HOUR, initialDelay = 10 * SECOND)
    public void doWork() {
        process(TaskList.POSTPONED_FIXTURE_TASK);
    }

    @Override
    public void processSingleTask() {
        Integer results = fixtureDao.updatePostponedFixtures();
        if (results > 0) {
            log.info("PostponedFixturesTask: updated {} {}", results, results > 1 ? "fixtures" : "fixture");
        }
    }
}
