package com.mySportPage.dataAcquisition.dao;

import com.mySportPage.model.SportObjectEnum;
import com.mySportPage.model.Stadium;
import com.mySportPage.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DataAcquisitionDao {

    private static final Logger log = LoggerFactory.getLogger(DataAcquisitionDao.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Map<SportObjectEnum, List<String>> queriesForChecking = new HashMap<>() {{
        put(SportObjectEnum.TEAM, List.of("SELECT EXISTS (SELECT 1 FROM public.teams WHERE team_id = :externalTeamId)"));
        put(SportObjectEnum.STADIUM, List.of("SELECT EXISTS (SELECT 1 FROM public.stadiums WHERE stadium_id = :externalStadiumId AND :externalTeamId = ANY(team_id))",
                "SELECT EXISTS (SELECT 1 FROM public.stadiums WHERE stadium_id = :externalStadiumId)"
        ));
    }};

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void persistTeams(List<Team> teams) {
        if (teams.isEmpty()) {
            return;
        }
        for (Team team : teams) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("externalTeamId", team.getExternalTeamId() != null ? team.getExternalTeamId() : null);
            parameters.addValue("name", team.getName() != null ? team.getName() : null);
            parameters.addValue("shortcut", team.getShortCut() != null ? team.getShortCut() : null);
            parameters.addValue("clubCrest", team.getClubCrest() != null ? team.getClubCrest() : null);
            parameters.addValue("clubFounded", team.getClubFounded() != null ? team.getClubFounded() : null);
            parameters.addValue("country", team.getCountry() != null ? team.getCountry() : null);

            String persistTeam = "INSERT INTO public.teams (team_id, name, shortcut, club_crest, club_founded, country) " +
                    "VALUES (:externalTeamId, :name, :shortcut, :clubCrest, :clubFounded, :country);";

            if (!checkIfObjectAlreadyExist(SportObjectEnum.TEAM, team.getExternalTeamId(), null)) {
                this.namedParameterJdbcTemplate.update(persistTeam, parameters);
                log.info("Team: " + team.getName() + " stored in db.");
            }
        }
    }

    public void persistStadiums(List<Stadium> stadiums) {
        if (stadiums.isEmpty()) {
            return;
        }
        for (Stadium stadium : stadiums) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", stadium.getId() != null ? stadium.getId() : null);
            parameters.addValue("stadium", stadium.getStadium() != null ? stadium.getStadium() : null);
            parameters.addValue("teamId", stadium.getExternalTeamId() != null ? stadium.getExternalTeamId() : null);
            parameters.addValue("capacity", stadium.getCapacity() != null ? stadium.getCapacity() : null);
            parameters.addValue("address", stadium.getAddress() != null ? stadium.getAddress() : null);
            parameters.addValue("city", stadium.getCity() != null ? stadium.getCity() : null);

            String persistStadium = "INSERT INTO public.stadiums (stadium_id, stadium, team_id, capacity, address, city) " +
                    "VALUES (:id, :stadium, ARRAY [:teamId], :capacity, :address, :city);";

            String updateStadium = "UPDATE public.stadiums SET team_id = array_append(team_id, :teamId) WHERE stadium_id = :id";

            if (!checkIfObjectAlreadyExist(SportObjectEnum.STADIUM, null, stadium.getId())) {
                this.namedParameterJdbcTemplate.update(persistStadium, parameters);
                log.info("Stadium: " + stadium.getStadium() + " stored in db.");
            } else if (!checkIfObjectAlreadyExist(SportObjectEnum.STADIUM, stadium.getExternalTeamId(), stadium.getId())) {
                this.namedParameterJdbcTemplate.update(updateStadium, parameters);
                log.info("Stadium: " + stadium.getStadium() + " now has additional team assigned to with id: " + stadium.getExternalTeamId());
            }
        }
    }


    private boolean checkIfObjectAlreadyExist(SportObjectEnum object, Integer externalTeamId, Integer externalStadiumId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("externalTeamId", externalTeamId);
        parameters.addValue("externalStadiumId", externalStadiumId);
        int queryIndex = externalTeamId != null ? 0 : 1;
        return Boolean.TRUE.equals(
                namedParameterJdbcTemplate.queryForObject(
                        queriesForChecking.get(object).get(queryIndex),
                        parameters,
                        Boolean.class));
    }
}
