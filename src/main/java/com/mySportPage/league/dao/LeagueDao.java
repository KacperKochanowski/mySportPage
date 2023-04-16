package com.mySportPage.league.dao;

import com.mySportPage.league.dto.LeagueDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class LeagueDao {

    @PersistenceContext
    private EntityManager entityManager;


    public List<LeagueDTO> getLeagues(String schema) {
        List<Object[]> results = entityManager
                .createNativeQuery(LeagueQueries.GET_LEAGUES.getQuery()
                        .replace("{schema}", schema))
                .getResultList();
        return mapToLeaguesList(results);
    }

    public List<LeagueDTO> getLeagues(String country, String schema) {
        List<Object[]> results = entityManager
                .createNativeQuery(LeagueQueries.GET_LEAGUES_BY_COUNTRY.getQuery()
                        .replace("{schema}", schema))
                .setParameter("country", country)
                .getResultList();
        return mapToLeaguesList(results);
    }

    private List<LeagueDTO> mapToLeaguesList(List<Object[]> results) {
        List<LeagueDTO> fixtures = new ArrayList<>();
        for (Object[] value : results) {
            LeagueDTO league = new LeagueDTO();
            league.setName((String) value[0]);
            league.setType((String) value[1]);
            league.setCountry((String) value[2]);
        }
        return fixtures;
    }
}
