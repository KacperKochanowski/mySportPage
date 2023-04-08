package com.mySportPage.dataAcquisition.dao;

import com.mySportPage.dataAcquisition.model.Fixture;
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

    public List<Fixture> getFixtures() {
        List<Fixture> fixtureList = new ArrayList<>();

        Query query = entityManager.createNamedQuery(FixtureQueries.GET_FIXTURES.getQuery());
        List<Object[]> results = query.getResultList();
        for (Object[] value : results) {
            Fixture fixture = new Fixture();
            fixture.setEvent((String) value[0]);
            fixture.setStart((Date) value[1]);
            fixture.setFinalScore((String) value[2]);
            fixtureList.add(fixture);
        }
        return fixtureList;
    }
}
