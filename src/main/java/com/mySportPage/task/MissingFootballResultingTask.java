package com.mySportPage.task;

import com.mySportPage.dao.FixtureDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MissingFootballResultingTask {

    private static final Logger log = LoggerFactory.getLogger(MissingFootballResultingTask.class);

    @Autowired
    private FixtureDao fixtureDao;

    private final long HOUR = 3_600_000;

    @Scheduled(fixedDelay = HOUR)
    private void checkForMissingResults() {
        log.info(">>>> STARTED MissingResultingTask <<<<");
        Integer issues = fixtureDao.checkForMissingResulting();
        if (issues > 0) {
            log.error("MissingResultingTask: found {} not settled results!", issues);
        }
        log.info(">>>> FINISHED MissingResultingTask <<<<");
    }
}
