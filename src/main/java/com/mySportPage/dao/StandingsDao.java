package com.mySportPage.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public class StandingsDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Object getStandings(Integer leagueId, String locationType) {
        JSONObject json = new JSONObject(Map.of("leagueId", leagueId, "locationType", locationType));
        return entityManager.createNativeQuery("SELECT football.get_team_standings(CAST(:json as JSONB)) AS standings")
                .setParameter("json", json.toString())
                .getSingleResult();
    }
}
