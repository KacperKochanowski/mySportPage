package com.mySportPage.dao;

import com.mySportPage.model.Stadium;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mySportPage.dao.queries.StadiumQueries.*;

@Repository
@SuppressWarnings("unchecked")
public class StadiumDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Stadium> getByCity(String city) {
        List<Object[]> results = entityManager.createNativeQuery(STADIUM_BY_CITY).setParameter("city", city).getResultList();
        return mapToStadiumsList(results);
    }

    public List<Stadium> getByAddress(String address) {
        List<Object[]> results = entityManager.createNativeQuery(STADIUM_BY_ADDRESS).setParameter("address", address).getResultList();
        return mapToStadiumsList(results);
    }

    public List<Stadium> getByTeamId(Integer teamId) {
        List<Object[]> results = entityManager.createNativeQuery(STADIUM_BY_TEAM_ID).setParameter("teamId", teamId).getResultList();
        return mapToStadiumsList(results);
    }

    public List<Stadium> getByTeamName(String teamName) {
        List<Object[]> results = entityManager.createNativeQuery(STADIUM_BY_TEAM_NAME).setParameter("teamName", teamName).getResultList();
        return mapToStadiumsList(results);
    }

    public List<Stadium> getByName(String name) {
        List<Object[]> results = entityManager.createNativeQuery(STADIUM_BY_NAME).setParameter("name", name).getResultList();
        return mapToStadiumsList(results);
    }

    private List<Stadium> mapToStadiumsList(List<Object[]> results) {
        List<Stadium> stadiums = new ArrayList<>();
        for (Object[] row : results) {
            stadiums.add(Stadium
                    .builder()
                    .withId((Integer) row[0])
                    .withName((String) row[1])
                    .withCapacity((Integer) row[2])
                    .withAddress((String) row[3])
                    .withCity((String) row[4])
                    .build());
        }
        return stadiums;
    }
}
