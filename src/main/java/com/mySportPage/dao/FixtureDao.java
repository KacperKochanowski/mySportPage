package com.mySportPage.dao;

import com.mySportPage.comonTools.Formatter;
import com.mySportPage.dao.queries.FixtureQueries;
import com.mySportPage.model.FixturePlaceEnum;
import com.mySportPage.model.dto.FixtureDTO;
import com.mySportPage.model.request.FixtureRequestModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Repository
@SuppressWarnings("unchecked")
public class FixtureDao {

    private static final String ALL_FIXTURES = "allFixtures";
    private static final String FIXTURES_FOR_TWO_WEEKS = "twoWeeksFixtures";
    private static final String FIXTURES_BY_TEAM = "fixturesByTeam";
    private static final String FIXTURES_BY_TEAM_AND_PLACE_OR_PLAYED = "fixturesByTeamAndWeatherPlayedOrPlace";
    private static final String FIXTURES_BY_LEAGUE_AND_ADDITIONALLY_ROUND = "fixturesByLeagueAndAdditionallyRound";

    @Autowired
    private CacheManager cacheManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(fixedRate = 3_600_000)
    public void evictCaches() {
        Objects.requireNonNull(cacheManager.getCache(ALL_FIXTURES)).clear();
        Objects.requireNonNull(cacheManager.getCache(FIXTURES_FOR_TWO_WEEKS)).clear();
        Objects.requireNonNull(cacheManager.getCache(FIXTURES_BY_TEAM)).clear();
        Objects.requireNonNull(cacheManager.getCache(FIXTURES_BY_TEAM_AND_PLACE_OR_PLAYED)).clear();
        Objects.requireNonNull(cacheManager.getCache(FIXTURES_BY_LEAGUE_AND_ADDITIONALLY_ROUND)).clear();
    }

    @Cacheable(ALL_FIXTURES)
    public List<FixtureDTO> getFixtures() {
        List<Object[]> results = entityManager
                .createNativeQuery(FixtureQueries.GET_FIXTURES.getQuery())
                .getResultList();
        return mapToFixturesList(results);
    }

    @Cacheable(FIXTURES_BY_LEAGUE_AND_ADDITIONALLY_ROUND)
    public Map<String, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round) {
        Query query = round != null ?
                entityManager
                        .createNativeQuery(FixtureQueries.GET_FIXTURES_BY_LEAGUE_ID_AND_ROUND_NO.getQuery())
                        .setParameter("round", round) :
                entityManager
                        .createNativeQuery(FixtureQueries.GET_FIXTURES_BY_LEAGUE_ID.getQuery());
        query.setParameter("leagueId", leagueId);
        List<Object[]> results = query.getResultList();
        List<FixtureDTO> fixtures = mapToFixturesList(results);
        return fixtures.stream().collect(Collectors.groupingBy(v -> String.format("round: %s", v.getRound())));
    }

    @Cacheable(FIXTURES_BY_TEAM_AND_PLACE_OR_PLAYED)
    public List<FixtureDTO> getFixtures(FixtureRequestModel requestModel) {
        FixturePlaceEnum place = requestModel.getPlace();
        Boolean isPlayed = requestModel.getPlayed();
        StringBuilder query = new StringBuilder(FixtureQueries.GET_FIXTURES_BY_TEAM_ID.getQuery());
        if (place != null) {
            if (place.equals(FixturePlaceEnum.HOME)) {
                query.append(FixtureQueries.GET_FIXTURES_BY_TEAM_ID_HOME.getQuery());
            } else {
                query.append(FixtureQueries.GET_FIXTURES_BY_TEAM_ID_AWAY.getQuery());
            }
        }
        if (isPlayed != null) {
            query.append(FixtureQueries.GET_FIXTURES_BY_TEAM_ID_AND_WHETHER_PLAYED.getQuery());
        }
        Query queryObj = entityManager.createNativeQuery(query.toString());
        queryObj.setParameter("teamId", requestModel.getTeamId());
        if (isPlayed != null) {
            queryObj.setParameter("played", isPlayed);
        }
        List<Object[]> results = queryObj.getResultList();
        return mapToFixturesList(results);
    }

    @Cacheable(value = FIXTURES_FOR_TWO_WEEKS)
    public Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound() {
        List<FixtureDTO> fixturesList = mapToFixturesList(entityManager.createNativeQuery(FixtureQueries.GET_FIXTURES_FOR_LAST_AND_NEXT_WEEK.getQuery())
                .getResultList());
        return fixturesList.stream().collect(
                Collectors.groupingBy(v -> String.valueOf(v.getStart()).substring(0, 10),
                        Collectors.groupingBy(FixtureDTO::getLeagueName,
                                Collectors.groupingBy(v -> String.format("round: %s", v.getRound()), toList()))));
    }

    public Integer checkForMissingResulting() {
        return (Integer) entityManager.createNativeQuery(FixtureQueries.ANY_MISSING_RESULT.getQuery())
                .getSingleResult();
    }

    public Map<FixtureDTO, Integer> getDataToUpdate() {
        List<Object[]> data = (List<Object[]>) entityManager.createNativeQuery(FixtureQueries.MISSING_RESULTS_WITH_LEAGUE.getQuery())
                .getResultList();
        return convertDataToMap(data);
    }

    @Transactional
    public Integer updatePostponedFixtures() {
        return entityManager.createNativeQuery(FixtureQueries.SET_POSTPONED_FIXTURES.getQuery()).executeUpdate();
    }

    private List<FixtureDTO> mapToFixturesList(List<Object[]> results) {
        List<FixtureDTO> fixtures = new ArrayList<>();
        for (Object[] value : results) {
            fixtures.add(FixtureDTO.builder()
                    .withLeagueId((Integer) value[0])
                    .withLeagueName((String) value[1])
                    .withEvent((String) value[2])
                    .withStart(Formatter.parseDate((Date) value[3], Formatter.dateTimeMin))
                    .withFinished((boolean) value[4])
                    .withResult((String) value[5])
                    .withRound((Integer) value[6])
                    .build());
        }
        return fixtures;
    }

    private Map<FixtureDTO, Integer> convertDataToMap(List<Object[]> results) {
        return results.stream()
                .collect(Collectors.toMap(
                        array -> FixtureDTO.builder()
                                .withLeagueId((Integer) array[0])
                                .withSeason((Integer) array[1])
                                .build(),
                        array -> (Integer) array[2]
                ));
    }
}
