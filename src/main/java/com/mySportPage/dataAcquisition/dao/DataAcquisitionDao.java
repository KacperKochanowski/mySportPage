package com.mySportPage.dataAcquisition.dao;

import com.mySportPage.model.SportObjectEnum;
import com.mySportPage.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DataAcquisitionDao {

    private static final Logger log = LoggerFactory.getLogger(DataAcquisitionDao.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Map<SportObjectEnum, String> queriesForChecking = new HashMap<>() {{
        put(SportObjectEnum.TEAM, "SELECT EXISTS (SELECT 1 FROM public.teams where team_id = :externalTeamId)");
        put(SportObjectEnum.STADIUM, "SELECT EXISTS (SELECT 1 FROM public.stadiums where team_id = :externalTeamId)");
    }};

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void persistTeams(List<Team> teams) {
        if(teams.isEmpty()){
            return;
        }
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String persistTeams = "INSERT INTO public.teams (team_id, name, shortcut, club_crest, club_founded, country) " +
                "VALUES (:externalTeamId, :name, :shortcut, :clubCrest, :clubFounded, :country); " +
                "RETURNING ";

        String persistStadium = "INSERT INTO public.stadiums (stadium, is_national, address_of_stadium, city_of_stadium, stadium_capacity, team_id) " +
                "VALUES (:stadium, :isNational, :addressOfStadium, :cityOfStadium, :capacity, :externalTeamId);";


        for (Team team : teams) {
            parameters.addValue("externalTeamId", team.getExternalTeamId() != null ? team.getExternalTeamId() : null);
            parameters.addValue("name", team.getName() != null ? team.getName() : null);
            parameters.addValue("shortcut", team.getShortCut() != null ? team.getShortCut() : null);
            parameters.addValue("clubCrest", team.getClubCrest() != null ? team.getClubCrest() : null);
            parameters.addValue("clubFounded", team.getClubFounded() != null ? team.getClubFounded() : null);
            parameters.addValue("country", team.getCountry() != null ? team.getCountry() : null);
            parameters.addValue("stadium", team.getStadium() != null ? team.getStadium() : null);
            parameters.addValue("isNational", team.isNational());
            parameters.addValue("addressOfStadium", team.getAddressOfStadium() != null ? team.getAddressOfStadium() : null);
            parameters.addValue("cityOfStadium", team.getCityOfStadium() != null ? team.getCityOfStadium() : null);
            parameters.addValue("capacity", team.getCapacity() != null ? team.getCapacity() : null);

            if(!checkIfObjectAlreadyExist(SportObjectEnum.TEAM, team.getExternalTeamId())) {
                this.namedParameterJdbcTemplate.update(persistTeams, parameters);
                log.info("Team: " + team.getName() + " stored in db.");
            }
            if(!checkIfObjectAlreadyExist(SportObjectEnum.STADIUM, team.getExternalTeamId())) {
                this.namedParameterJdbcTemplate.update(persistStadium, parameters);
                log.info("Stadium: " + team.getStadium() + " stored in db.");
            }
        }
    }

    public boolean checkIfObjectAlreadyExist(SportObjectEnum object, Integer externalId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        return Boolean.TRUE.equals(
                namedParameterJdbcTemplate.queryForObject(
                        queriesForChecking.get(object),
                        parameters.addValue("externalTeamId", externalId),
                        Boolean.class));
    }
}
