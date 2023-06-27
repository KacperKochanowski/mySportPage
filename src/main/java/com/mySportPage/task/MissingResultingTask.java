package com.mySportPage.task;

import com.mySportPage.dao.FixtureDao;
import com.mySportPage.model.SportEnum;
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

    private final Integer HOUR = 3_600_000;

    @Scheduled(fixedDelay = HOUR)
    private void checkForMissingResults() {
        log.info(">>>> STARTED MissingResultingTask <<<<");
        Integer issues = fixtureDao.checkForMissingResulting(SportEnum.FOOTBALL.getSchema());
        if (issues > 0) {
            log.error("MissingResultingTask: found {} not settled results!", issues);
        }
        log.info(">>>> FINISHED MissingResultingTask <<<<");
    }
}
