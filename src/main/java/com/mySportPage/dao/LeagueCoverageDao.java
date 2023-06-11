package com.mySportPage.dao;

import com.mySportPage.dao.queries.LeagueCoverageQueries;
import com.mySportPage.model.LeagueCoverage;
import com.mySportPage.model.SportEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@SuppressWarnings("unchecked")
public class LeagueCoverageDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Map<SportEnum, List<LeagueCoverage>> getCoverageForAllLeagues() {
        Map<SportEnum, List<LeagueCoverage>> coverageMap = new HashMap<>();
        for (SportEnum sport : SportEnum.values()) {
            List<Object[]> results = entityManager
                    .createNativeQuery(LeagueCoverageQueries.GET_COVERAGE.getQuery()
                            .replace("{schema}", sport.getSchema()))
                    .getResultList();
            coverageMap.put(sport, mapToLeagueCoverage(results));
        }
        return coverageMap;
    }

    private List<LeagueCoverage> mapToLeagueCoverage(List<Object[]> results) {
        return results.stream()
                .map(element -> LeagueCoverage
                        .builder()
                        .externalLeagueId((Integer) element[0])
                        .withEvents((boolean) element[1])
                        .withLineups((boolean) element[2])
                        .withStatisticsFixtures((boolean) element[3])
                        .withStatisticsPlayers((boolean) element[4])
                        .withStandings((boolean) element[5])
                        .withPlayers((boolean) element[6])
                        .withTopScorers((boolean) element[7])
                        .withTopAssists((boolean) element[8])
                        .withTopCards((boolean) element[9])
                        .withInjuries((boolean) element[10])
                        .withPredictions((boolean) element[11])
                        .withOdds((boolean) element[12])
                        .build())
                .collect(Collectors.toList());
    }
}
