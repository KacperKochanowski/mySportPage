package com.mySportPage.dao;

import com.mySportPage.dao.queries.FixtureStatisticsQueries;
import com.mySportPage.model.FixtureStatistics;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@SuppressWarnings("unchecked")
public class FixtureStatisticsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Map<Integer, List<FixtureStatistics>> getFixtureStatistics(Integer fixtureId, String schema) {
        List<Object[]> results = entityManager.createNativeQuery(
                        FixtureStatisticsQueries.GET_FIXTURES_BY_FIXTURE_ID.getQuery().replace("{schema}", schema))
                .setParameter("fixtureId", fixtureId)
                .getResultList();
        return mapToFixtureStatistics(results);
    }


    private Map<Integer, List<FixtureStatistics>> mapToFixtureStatistics(List<Object[]> results) {
            //TODO: implement mapping

        return null;
    }
}
