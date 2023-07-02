package com.mySportPage.dao;

import com.mySportPage.dao.queries.FixtureStatisticsQueries;
import com.mySportPage.model.FixtureStatistics;
import com.mySportPage.model.FixtureStatisticsEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@SuppressWarnings("unchecked")
public class FixtureStatisticsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Map<Integer, FixtureStatistics> getFixtureStatistics(Integer fixtureId, String schema) {
        List<Object[]> results = entityManager.createNativeQuery(
                        FixtureStatisticsQueries.GET_FIXTURES_BY_FIXTURE_ID.getQuery().replace("{schema}", schema))
                .setParameter("fixtureId", fixtureId)
                .getResultList();
        return mapToFixtureStatistics(results);
    }


    private Map<Integer, FixtureStatistics> mapToFixtureStatistics(List<Object[]> results) {
        Map<Integer, FixtureStatistics> statistics = new HashMap<>();
        for (Object[] result : results) {
            Map<FixtureStatisticsEnum, String> fixtureStatistics = new HashMap<>();
            for (int i = 0; i <= result.length; i++) {

            }
        }
        return statistics;
    }
}
