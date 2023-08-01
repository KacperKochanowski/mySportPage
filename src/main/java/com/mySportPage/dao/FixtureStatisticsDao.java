package com.mySportPage.dao;

import com.mySportPage.dao.queries.FixtureStatisticsQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mySportPage.model.FixtureStatisticsEnum.*;

@Repository
@SuppressWarnings("unchecked")
public class FixtureStatisticsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Map<Integer, Map<String, Object>> getFixtureStatistics(Integer fixtureId, String schema) {
        List<Object[]> results = entityManager.createNativeQuery(
                        FixtureStatisticsQueries.GET_FIXTURES_BY_FIXTURE_ID.getQuery().replace("{schema}", schema))
                .setParameter("fixtureId", fixtureId)
                .getResultList();
        return mapToFixtureStatistics(results);
    }


    private Map<Integer, Map<String, Object>> mapToFixtureStatistics(List<Object[]> results) {
        Map<Integer, Map<String, Object>> statistics = new HashMap<>();
        for (Object[] result : results) {
            int i = 0;
            Integer eventId;
            Map<String, Object> fixtureStatistics = new HashMap<>();
            fixtureStatistics.put(SHOTS_ON_GOAL.getCamelCaseName(), result[i]);
            fixtureStatistics.put(SHOTS_OFF_GOAL.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(TOTAL_SHOTS.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(BLOCKED_SHOTS.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(SHOTS_INSIDE_BOX.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(SHOTS_OUTSIDE_BOX.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(FOULS.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(CORNER_KICKS.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(OFFSIDES.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(BALL_POSSESSION.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(YELLOW_CARDS.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(RED_CARDS.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(GOALKEEPER_SAVES.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(TOTAL_PASSES.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(PASSES_ACCURATE.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(CORRECT_PASSES_PERCENT.getCamelCaseName(), result[++i]);
            fixtureStatistics.put(EXPECTED_GOALS.getCamelCaseName(), result[++i]);
            eventId = (Integer) result[++i];
            statistics.put(eventId, fixtureStatistics);
        }
        return statistics;
    }
}
