package com.mySportPage.task;

import com.mySportPage.cache.CoachContainer;
import com.mySportPage.dao.CoachDao;
import com.mySportPage.model.Coach;
import com.mySportPage.task.core.BaseTask;
import com.mySportPage.task.core.TaskList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CoachTask extends BaseTask {

    private static final Logger log = LoggerFactory.getLogger(CoachTask.class);

    @Autowired
    private CoachDao coachDao;

    @Scheduled(fixedDelay = 2 * BaseTask.HOUR)
    public void doWork() {
        process(TaskList.COACH_TASK);
    }

    @Override
    public void processSingleTask() {
        List<Coach> coaches = coachDao.getAllCoaches();
        log.debug("CoachTask: fetched {} coaches", coaches.size());
        Map<Integer, List<Coach>> filteredCoaches = coaches.stream().collect(Collectors.groupingBy(Coach::getLeagueId));
        CoachContainer.setCoaches(filteredCoaches);
    }
}
