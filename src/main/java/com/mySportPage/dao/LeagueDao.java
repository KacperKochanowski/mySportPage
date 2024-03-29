package com.mySportPage.dao;

import com.mySportPage.dao.queries.LeagueQueries;
import com.mySportPage.model.dto.LeagueDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class LeagueDao {

    @PersistenceContext
    private EntityManager entityManager;


    public List<LeagueDTO> getLeagues() {
        List<Object[]> results = entityManager
                .createNativeQuery(LeagueQueries.GET_LEAGUES.getQuery())
                .getResultList();
        return mapToLeaguesList(results);
    }

    public List<LeagueDTO> getLeagues(String country) {
        List<Object[]> results = entityManager
                .createNativeQuery(LeagueQueries.GET_LEAGUES_BY_COUNTRY.getQuery())
                .setParameter("country", country)
                .getResultList();
        return mapToLeaguesList(results);
    }

    public boolean anyPlays() {
        return (boolean) entityManager.createNativeQuery(LeagueQueries.ANY_LEAGUE_PLAYS.getQuery()).getSingleResult();
    }

    private List<LeagueDTO> mapToLeaguesList(List<Object[]> results) {
        List<LeagueDTO> fixtures = new ArrayList<>();
        for (Object[] value : results) {
            fixtures.add(LeagueDTO.builder()
                    .withName((String) value[0])
                    .withType((String) value[1])
                    .withCountry((String) value[2])
                    .build());
        }
        return fixtures;
    }
}
