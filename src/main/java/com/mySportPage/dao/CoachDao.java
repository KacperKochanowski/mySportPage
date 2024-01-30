package com.mySportPage.dao;

import com.mySportPage.model.Coach;
import com.mySportPage.model.request.CoachRequestModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mySportPage.dao.queries.CoachQueries.*;

@SuppressWarnings("unchecked")
@Repository
public class CoachDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Coach> getAllCoaches() {
        List<Object[]> results = entityManager.createNativeQuery(GET_ALL_COACHES.getQuery()).getResultList();
        return mapToCoachList(results);
    }

    public List<Coach> getCoaches(CoachRequestModel requestModel) {
        StringBuilder coachQuery = new StringBuilder().append(CORE_QUERY.getQuery());
        if (requestModel.getLeagueId() != null) {
            coachQuery
                    .append(AND.getQuery())
                    .append(GET_COACH_BY_LEAGUE_ID.getQuery());
        }
        if (requestModel.getTeamId() != null) {
            coachQuery
                    .append(AND.getQuery())
                    .append(GET_COACH_BY_TEAM_ID.getQuery());
        }
        if (requestModel.getCountry() != null) {
            coachQuery
                    .append(AND.getQuery())
                    .append(GET_COACH_BY_COUNTRY.getQuery());
        }
        Query query = entityManager.createNativeQuery(coachQuery.toString());

        if (requestModel.getLeagueId() != null) {
            query.setParameter("leagueId", requestModel.getLeagueId());
        }
        if (requestModel.getTeamId() != null) {
            query.setParameter("teamId", requestModel.getTeamId());
        }
        if (requestModel.getCountry() != null) {
            query.setParameter("country", requestModel.getCountry());

        }

        List<Object[]> result = query.getResultList();
        return mapToCoachList(result);
    }


    private List<Coach> mapToCoachList(List<Object[]> result) {
        List<Coach> coaches = new ArrayList<>();

        for (Object[] row : result) {
            Coach coach = Coach.builder()
                    .withLeagueId((Integer) row[0])
                    .withName((String) row[1])
                    .withFirstName((String) row[2])
                    .withLastName((String) row[3])
                    .withAge((int) row[4])
                    .withBirthDate((Date) row[5])
                    .withNationality((String) row[6])
                    .withPhoto((String) row[7])
                    .withTeamId((Integer) row[8])
                    .build();
            coaches.add(coach);
        }
        return coaches;
    }
}
