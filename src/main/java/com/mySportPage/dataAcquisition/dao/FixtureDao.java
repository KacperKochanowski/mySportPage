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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    public Map<Integer, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round) {
        List<FixtureDTO> list = new ArrayList<>();
        Query query = round != null ?
                entityManager.createNativeQuery(FixtureQueries.GET_FIXTURES_BY_LEAGUE_ID_AND_ROUND_NO.getQuery()).setParameter("round", round) :
                entityManager.createNativeQuery(FixtureQueries.GET_FIXTURES_BY_LEAGUE_ID.getQuery());
        query.setParameter("leagueId", leagueId);
        List<Object[]> results = query.getResultList();
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
