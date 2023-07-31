package com.mySportPage.dao;

import com.mySportPage.dao.queries.CoachQueries;
import com.mySportPage.model.Coach;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository
public class CoachDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Coach> getCoachesByLeague(int leagueId, String schema) {
        List<Object[]> result = entityManager.createNativeQuery(CoachQueries.GET_COACH_BY_LEAGUE_ID.getQuery()
                        .replace("{schema}", schema))
                .setParameter("leagueId", leagueId)
                .getResultList();
        return mapToCoachList(result);
    }


    private List<Coach> mapToCoachList(List<Object[]> result) {
        List<Coach> coaches = new ArrayList<>();

        for (Object[] row : result) {
            Coach coach = Coach.builder()
                    .withExternalId((Integer) row[0])
                    .withName((String) row[1])
                    .withFirstName((String) row[2])
                    .withLastName((String) row[3])
                    .withAge((int) row[4])
                    .withBirthDate((Date) row[5])
                    .withBirthCountry((String) row[6])
                    .withNationality((String) row[7])
                    .withHeight((Integer) row[8])
                    .withWeight((Integer) row[9])
                    .withPhoto((String) row[10])
                    .build();
            coaches.add(coach);
        }
        return coaches;
    }
}
