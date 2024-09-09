package com.mySportPage.task;

import com.mySportPage.dao.FixtureDao;
import com.mySportPage.model.dto.FixtureDTO;
import com.mySportPage.rest.DataAcquisitionRestService;
import com.mySportPage.task.core.BaseTask;
import com.mySportPage.task.core.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

import static com.mySportPage.comonTools.TimeUnits.HOUR;
import static com.mySportPage.comonTools.TimeUnits.SECOND;

@Component
@Profile("production")
public class FixtureUpdaterTask extends BaseTask {

    @Autowired
    private FixtureDao fixtureDao;

    @Autowired
    private DataAcquisitionRestService dataAcquisitionRest;

    @Value("${setting.MIN_VALUE_OF_MISSING_RESULTS_TO_ALLOW_UPDATE:10}")
    private int minValueToAllowUpdate;

    @Scheduled(fixedDelay = 12 * HOUR, initialDelay = 4 * SECOND)
    public void doWork() {
        process(TaskList.FIXTURE_UPDATER_TASK);
    }

    @Override
    public void processSingleTask() {
        Map<FixtureDTO, Integer> issues = fixtureDao.getDataToUpdate().entrySet().stream()
                .filter(e -> e.getValue() > minValueToAllowUpdate)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        for (var entry : issues.entrySet()) {
            if (entry.getValue() < minValueToAllowUpdate) {
                log.debug("FixtureUpdaterTask: not enough events found to perform update league ID = {} - minimum is {}", entry.getKey().getLeagueId(), minValueToAllowUpdate);
                continue;
            }
            //TODO: uncomment it!
//            dataAcquisitionRest.createFixtures(String.valueOf(entry.getKey().getLeagueId()), entry.getKey().getSeason());
        }
    }
}
