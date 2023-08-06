package com.mySportPage.dao;

import com.mySportPage.model.Coach;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mySportPage.dao.queries.CoachQueries.*;

@SuppressWarnings("unchecked")
@Repository
public class CoachDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Coach> getCoachesByLeague(int leagueId) {
        List<Object[]> result = entityManager.createNativeQuery(CORE_QUERY.getQuery() + GET_COACH_BY_LEAGUE_ID.getQuery())
                .setParameter("leagueId", leagueId)
                .getResultList();
        return mapToCoachList(result);
    }

    public List<Coach> getCoachesByTeam(int teamId) {
        List<Object[]> result = entityManager.createNativeQuery(CORE_QUERY.getQuery() + GET_COACH_BY_TEAM_ID.getQuery())
                .setParameter("teamId", teamId)
                .getResultList();
        return mapToCoachList(result);
    }


    public List<Coach> getCoachesByCountryCode(String countryCode) {
        List<Object[]> result = entityManager.createNativeQuery(CORE_QUERY.getQuery() + GET_COACH_BY_COUNTRY_CODE.getQuery())
                .setParameter("countryCode", countryCode)
                .getResultList();
        return mapToCoachList(result);
    }

    public List<Coach> getCoaches(Map<String, Object> params) {
        StringBuilder coachQuery = new StringBuilder().append(CORE_QUERY.getQuery());
        boolean isFirstParam = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            switch (entry.getKey()) {
                case "leagueId" -> {
                    isFirstParam(coachQuery, isFirstParam);
                    coachQuery.append(GET_COACH_BY_LEAGUE_ID.getQuery());
                }
                case "teamId" -> {
                    isFirstParam(coachQuery, isFirstParam);
                    coachQuery.append(GET_COACH_BY_TEAM_ID.getQuery());
                }
                case "languageCode" -> {
                    isFirstParam(coachQuery, isFirstParam);
                    coachQuery.append(GET_COACH_BY_COUNTRY_CODE.getQuery());
                }
            }
            isFirstParam = false;
        }
        Query query = entityManager.createNativeQuery(coachQuery.toString());

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        List<Object[]> result = query.getResultList();
        return mapToCoachList(result);
    }

    private void isFirstParam(StringBuilder query, boolean firstParam) {
        if (!firstParam) {
            query.append(AND.getQuery());
        }
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
                    .withNationality((String) row[6])
                    .withHeight((Integer) row[7])
                    .withWeight((Integer) row[8])
                    .withPhoto((String) row[9])
                    .build();
            coaches.add(coach);
        }
        return coaches;
    }
}
