package com.mySportPage.dao;

import com.mySportPage.dao.queries.TeamQueries;
import com.mySportPage.model.dto.TeamDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class TeamDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TeamDTO> getTeam(Integer teamId) {
        List<Object[]> result = entityManager
                .createNativeQuery(TeamQueries.GET_TEAM_BY_TEAM_ID.getQuery())
                .setParameter("teamId", teamId)
                .getResultList();
        return mapToTeam(result);
    }

    public List<TeamDTO> getTeamByLeague(Integer leagueId) {
        List<Object[]> result = entityManager
                .createNativeQuery(TeamQueries.GET_TEAM_BY_LEAGUE_ID.getQuery())
                .setParameter("leagueId", leagueId)
                .getResultList();
        return mapToTeam(result);
    }

    public List<TeamDTO> getTeamByCountryName(String countryName) {
        List<Object[]> result = entityManager
                .createNativeQuery(TeamQueries.GET_TEAM_BY_COUNTRY_NAME_ID.getQuery())
                .setParameter("countryName", countryName)
                .getResultList();
        return mapToTeam(result);
    }


    private List<TeamDTO> mapToTeam(List<Object[]> results) {
        List<TeamDTO> teams = new ArrayList<>();
        for (Object[] result : results) {
            teams.add(TeamDTO.builder()
                    .withName((String) result[0])
                    .withShortCut((String) result[1])
                    .withClubFounded((Integer) result[2])
                    .withCountry((String) result[3])
                    .withLeagueId((Integer) result[4])
                    .build());
        }
        return teams;
    }
}
