package com.mySportPage.dao;

import com.mySportPage.model.CoachCareer;
import com.mySportPage.model.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CoachCareerDao {

    @PersistenceContext
    private EntityManager entityManager;

    String coachCareerByIdQuery =
            "SELECT cc.team_id, t.name, cc.start, cc.end " +
                    "FROM football.coach_career cc " +
                    "LEFT JOIN football.team t on cc.team_id = t.team_id " +
                    "WHERE coach_id = :coachId " +
                    "ORDER BY start DESC";

    String coachCareerByName =
            "SELECT cc.team_id, t.name, cc.start, cc.end " +
                    "FROM football.coach_career cc " +
                    "JOIN football.coach c on c.external_id = cc.coach_id " +
                    "LEFT JOIN football.team t on cc.team_id = t.team_id " +
                    "WHERE c.name ILIKE CAST(CONCAT('%' || :coachName || '%') AS TEXT) " +
                    "ORDER BY start DESC";


    public List<CoachCareer> getCoachCareerById(Integer coachId) {
        List<Object[]> coachCareerData = entityManager.createNativeQuery(coachCareerByIdQuery)
                .setParameter("coachId", coachId)
                .getResultList();
        return mapToCoachCareer(coachCareerData);
    }

    public List<CoachCareer> getCoachCareerByName(String coachName) {
        List<Object[]> coachCareerData = entityManager.createNativeQuery(coachCareerByName)
                .setParameter("coachName", coachName)
                .getResultList();
        return mapToCoachCareer(coachCareerData);
    }

    private List<CoachCareer> mapToCoachCareer(List<Object[]> coachCareerData) {
        List<CoachCareer> coachCareer = new ArrayList<>();
        for (Object[] data : coachCareerData) {
            coachCareer.add(CoachCareer
                    .builder()
                    .withTeam(new Team((Integer) data[0], (String) data[1]))
                    .withStart((Date) data[2])
                    .withEnd((Date) data[3])
                    .build());
        }
        return coachCareer;
    }
}