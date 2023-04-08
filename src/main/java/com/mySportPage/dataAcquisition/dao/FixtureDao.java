package com.mySportPage.dataAcquisition.dao;

import com.mySportPage.dataAcquisition.model.FixtureDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FixtureDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<FixtureDTO> getFixtures() {
        List<FixtureDTO> fixtureList = new ArrayList<>();
        List<Object[]> results = entityManager.createNativeQuery(FixtureQueries.GET_FIXTURES.getQuery()).getResultList();
        for (Object[] value : results) {
            FixtureDTO fixture = new FixtureDTO();
            fixture.setLeagueId((Integer) value[0]);
            fixture.setEvent((String) value[1]);
            fixture.setStart((Date) value[2]);
            fixture.setFinished((boolean) value[3]);
            fixture.setResult((String) value[4]);
            fixture.setRound((Integer) value[5]);
            fixtureList.add(fixture);
        }
        return fixtureList;
    }

    public Map<Integer, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round) {
        List<FixtureDTO> list = new ArrayList<>();
        String query = FixtureQueries.GET_FIXTURES_BY_LEAGUE_ID.getQuery();
        Query queryObject;
        if (round != null && round > 0) {
            query += query + "AND round = :round ";
            queryObject = entityManager.createNativeQuery(query);
            queryObject.setParameter("round", round);
        } else {
            queryObject = entityManager.createNativeQuery(query);
        }
        queryObject.setParameter("leagueId", leagueId);
        List<Object[]> results = queryObject.getResultList();
        for (Object[] value : results) {
            FixtureDTO fixture = new FixtureDTO();
            fixture.setLeagueId((Integer) value[0]);
            fixture.setEvent((String) value[1]);
            fixture.setStart((Date) value[2]);
            fixture.setFinished((boolean) value[3]);
            fixture.setResult((String) value[4]);
            fixture.setRound((Integer) value[5]);
            list.add(fixture);
        }
        return list.stream().collect(Collectors.groupingBy(FixtureDTO::getRound));
    }
}
