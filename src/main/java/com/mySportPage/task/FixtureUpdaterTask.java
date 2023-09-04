package com.mySportPage.task;

import com.mySportPage.dao.FixtureDao;
import com.mySportPage.rest.DataAcquisitionRest;
import com.mySportPage.task.core.BaseTask;
import com.mySportPage.task.core.TaskList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.stream.Collectors;

public class FixtureUpdaterTask extends BaseTask {

    private static final Logger log = LoggerFactory.getLogger(FixtureUpdaterTask.class);

    @Autowired
    private FixtureDao fixtureDao;

    @Autowired
    private DataAcquisitionRest dataAcquisitionRest;

    @Value("${setting.MIN_VALUE_OF_MISSING_RESULTS_TO_ALLOW_UPDATE:10}")
    private int minValueToAllowUpdate;

    @Scheduled(fixedDelay = 12 * BaseTask.HOUR)
    public void doWork() {
        String taskName = TaskList.FIXTURE_UPDATER_TASK.getTaskName();
        if (isEnabled(taskName)) {
            process(TaskList.FIXTURE_UPDATER_TASK);
        } else {
            log.debug("{} is currently disabled.", taskName);
        }
    }

    @Override
    public void processSingleTask() {
        Map<String, Integer> issues = fixtureDao.getDataToUpdate().entrySet().stream()
                .filter(e -> e.getValue() > minValueToAllowUpdate)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        if (issues.isEmpty()) {
            log.debug("MissingResultingTask: not enough events found to perform their update. Found {}, and minimum is {}", issues, minValueToAllowUpdate);
        } else {
            for (var entry : issues.entrySet()) {
                dataAcquisitionRest.createFixtureStatistics(entry.getValue(), null);
            }
        }
    }
}
