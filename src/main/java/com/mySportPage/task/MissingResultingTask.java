package com.mySportPage.task;

import com.mySportPage.dao.FixtureDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MissingResultingTask {

    private static final Logger log = LoggerFactory.getLogger(MissingResultingTask.class);

    @Autowired
    private FixtureDao fixtureDao;

    private final long HOUR = 3_600_000;

    @Scheduled(fixedDelay = HOUR)
    private void checkForMissingResults() {
        Integer issues = fixtureDao.checkForMissingResulting();
        if (issues > 0) {
            log.warn("MissingResultingTask: found {} not settled results!", issues);
        }
    }
}
