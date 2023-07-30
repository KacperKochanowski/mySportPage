package com.mySportPage.dao;

import com.google.gson.Gson;
import com.mySportPage.dao.queries.CoachQueries;
import com.mySportPage.model.Coach;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class CoachDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Coach> getCoachesByLeague(String schema, int leagueId) {
        List<Object[]> result = entityManager.createNativeQuery(CoachQueries.GET_COACH_BY_LEAGUE_ID.getQuery()
                        .replace("schema", schema))
                .setParameter("leagueId", leagueId)
                .getResultList();
        return mapToCoachList(result);
    }

    private List<Coach> mapToCoachList(List<Object[]> result) {
        Gson gson = new Gson();
        List<Coach> coaches = new ArrayList<>();
        for (Object[] object : result) {
            Coach coach = gson.fromJson(gson.toJson(object), Coach.class);
            coaches.add(coach);
        }
        return coaches;
    }
}
