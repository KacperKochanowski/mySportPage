package com.mySportPage.dao;

import com.mySportPage.dao.queries.TeamQueries;
import com.mySportPage.model.dto.TeamDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
        return TeamDTO.builder()
                .withName((String) result[0])
                .withShortCut((String) result[1])
                .withClubFounded((Integer) result[2])
                .withCountry((String) result[3])
                .build();
    }
}
