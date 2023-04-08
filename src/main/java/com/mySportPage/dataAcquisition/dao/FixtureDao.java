package com.mySportPage.dataAcquisition.dao;

import com.mySportPage.dataAcquisition.model.FixtureDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FixtureDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<FixtureDTO> getFixtures() {
        List<FixtureDTO> fixtureList = new ArrayList<>();

        Query query = entityManager.createNativeQuery(FixtureQueries.GET_FIXTURES.getQuery());
        List<Object[]> results = query.getResultList();
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
}
