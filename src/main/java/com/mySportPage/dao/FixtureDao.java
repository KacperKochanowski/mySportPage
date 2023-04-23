package com.mySportPage.dao;

import com.mySportPage.dao.queries.FixtureQueries;
import com.mySportPage.dao.dto.FixtureDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Repository
@SuppressWarnings("unchecked")
public class FixtureDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<FixtureDTO> getFixtures(String schema) {
        List<Object[]> results = entityManager
                .createNativeQuery(FixtureQueries.GET_FIXTURES.getQuery()
                        .replace("{schema}", schema))
                .getResultList();
        return mapToFixturesList(results);
    }

    public Map<String, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round, String schema) {
        Query query = round != null ?
                entityManager
                        .createNativeQuery(FixtureQueries.GET_FIXTURES_BY_LEAGUE_ID_AND_ROUND_NO.getQuery()
                                .replace("{schema}", schema))
                        .setParameter("round", round) :
                entityManager
                        .createNativeQuery(FixtureQueries.GET_FIXTURES_BY_LEAGUE_ID.getQuery()
                                .replace("{schema}", schema));
        query.setParameter("leagueId", leagueId);
        List<Object[]> results = query.getResultList();
        List<FixtureDTO> fixtures = mapToFixturesList(results);
        return fixtures.stream().collect(Collectors.groupingBy(v -> String.format("round: %s", v.getRound())));
    }

    public List<FixtureDTO> getFixtures(Integer teamId, String place, String schema) {
        Query query;
        switch (place) {
            case null:
                query = entityManager
                        .createNativeQuery(FixtureQueries.GET_FIXTURES_BY_TEAM_ID.getQuery()
                                .replace("{schema}", schema));
                break;
            case "home":
                query = entityManager
                        .createNativeQuery(FixtureQueries.GET_FIXTURES_BY_TEAM_ID_HOME.getQuery()
                                .replace("{schema}", schema));
                break;
            case "away":
                query = entityManager
                        .createNativeQuery(FixtureQueries.GET_FIXTURES_BY_TEAM_ID_AWAY.getQuery()
                                .replace("{schema}", schema));
                break;
            default:
                return new ArrayList<>();
        }

        List<Object[]> results = query
                .setParameter("teamId", teamId)
                .getResultList();
        return mapToFixturesList(results);
    }

    public List<FixtureDTO> getFixtures(Integer teamId, boolean played, String schema) {
        List<Object[]> results = entityManager
                .createNativeQuery(FixtureQueries.GET_FIXTURES_BY_TEAM_ID_AND_WHETHER_PLAYED.getQuery()
                        .replace("{schema}", schema))
                .setParameter("played", played)
                .setParameter("teamId", teamId)
                .getResultList();
        return mapToFixturesList(results);
    }

    public Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound(String schema) {
        List<FixtureDTO> fixturesList = mapToFixturesList(entityManager.createNativeQuery(FixtureQueries.GET_FIXTURES_FOR_LAST_AND_NEXT_WEEK.getQuery()
                        .replace("{schema}", schema))
                .getResultList());
        return fixturesList.stream().collect(
                Collectors.groupingBy(v -> String.valueOf(v.getStart()).substring(0, 10),
                        Collectors.groupingBy(FixtureDTO::getLeagueName,
                                Collectors.groupingBy(v -> String.format("round: %s", v.getRound()), toList()))));
    }

    private List<FixtureDTO> mapToFixturesList(List<Object[]> results) {
        List<FixtureDTO> fixtures = new ArrayList<>();
        for (Object[] value : results) {
            FixtureDTO fixture = new FixtureDTO();
            fixture.setLeagueId((Integer) value[0]);
            fixture.setLeagueName((String) value[1]);
            fixture.setEvent((String) value[2]);
            fixture.setStart((Date) value[3]);
            fixture.setFinished((boolean) value[4]);
            fixture.setResult((String) value[5]);
            fixture.setRound((Integer) value[6]);
            fixtures.add(fixture);
        }
        return fixtures;
    }
}