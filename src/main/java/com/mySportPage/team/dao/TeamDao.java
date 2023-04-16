package com.mySportPage.team.dao;

import com.mySportPage.team.dto.TeamDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class TeamDao {

    @PersistenceContext
    private EntityManager entityManager;

    public TeamDTO getTeam(Integer teamId, String schema) {
        List<Object[]> result = entityManager
                .createNativeQuery(TeamQueries.GET_TEAM_BY_TEAM_ID.getQuery()
                        .replace("{schema}", schema))
                .setParameter("teamId", teamId)
                .getResultList();
        return mapToTeam(result.get(0));
    }


    private TeamDTO mapToTeam(Object[] result) {
        TeamDTO team = new TeamDTO();
        team.setName((String) result[0]);
        team.setShortCut((String) result[1]);
        team.setClubFounded((Integer) result[2]);
        team.setCountry((String) result[3]);
        return team;
    }
}
