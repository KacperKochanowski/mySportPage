package com.mySportPage.dataAcquisition.dao;

import com.mySportPage.model.*;
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

    private final Map<SportObjectEnum, List<String>> queriesForChecking = new HashMap<>() {{
        put(SportObjectEnum.TEAM, List.of("SELECT EXISTS (SELECT 1 FROM public.teams WHERE team_id = :internalId)"));
        put(SportObjectEnum.STADIUM, List.of("SELECT EXISTS (SELECT 1 FROM public.stadiums WHERE stadium_id = :externalStadiumId AND :internalId = ANY(team_id))",
                "SELECT EXISTS (SELECT 1 FROM public.stadiums WHERE stadium_id = :externalStadiumId)"));
        put(SportObjectEnum.LEAGUE, List.of("SELECT EXISTS (SELECT 1 FROM public.leagues WHERE league_id = :internalId AND country = ':externalStadiumId')"));
    }};

    @Autowired
    private void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void persistTeams(List<Team> teams) {
        if (teams.isEmpty()) {
            return;
        }
        String persistTeam = "INSERT INTO public.teams (team_id, name, shortcut, club_crest, club_founded, country) " +
                "VALUES (:externalTeamId, :name, :shortcut, :clubCrest, :clubFounded, :country);";

        for (Team team : teams) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("externalTeamId", team.getExternalTeamId() != null ? team.getExternalTeamId() : null);
            parameters.addValue("name", team.getName() != null ? team.getName() : null);
            parameters.addValue("shortcut", team.getShortCut() != null ? team.getShortCut() : null);
            parameters.addValue("clubCrest", team.getClubCrest() != null ? team.getClubCrest() : null);
            parameters.addValue("clubFounded", team.getClubFounded() != null ? team.getClubFounded() : null);
            parameters.addValue("country", team.getCountry() != null ? team.getCountry() : null);

            if (!checkIfObjectAlreadyExist(SportObjectEnum.TEAM, team.getExternalTeamId(), null)) {
                this.namedParameterJdbcTemplate.update(persistTeam, parameters);
                log.info("Team: {} stored in db.", team.getName());
            }
        }
    }

    public void persistStadiums(List<Stadium> stadiums) {
        if (stadiums.isEmpty()) {
            return;
        }
        String persistStadium = "INSERT INTO public.stadiums (stadium_id, stadium, team_id, capacity, address, city) " +
                "VALUES (:id, :stadium, ARRAY [:teamId], :capacity, :address, :city);";

        String updateStadium = "UPDATE public.stadiums SET team_id = array_append(team_id, :teamId) WHERE stadium_id = :id";

        for (Stadium stadium : stadiums) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", stadium.getId() != null ? stadium.getId() : null);
            parameters.addValue("stadium", stadium.getStadium() != null ? stadium.getStadium() : null);
            parameters.addValue("teamId", stadium.getExternalTeamId() != null ? stadium.getExternalTeamId() : null);
            parameters.addValue("capacity", stadium.getCapacity() != null ? stadium.getCapacity() : null);
            parameters.addValue("address", stadium.getAddress() != null ? stadium.getAddress() : null);
            parameters.addValue("city", stadium.getCity() != null ? stadium.getCity() : null);

            if (!checkIfObjectAlreadyExist(SportObjectEnum.STADIUM, null, stadium.getId())) {
                this.namedParameterJdbcTemplate.update(persistStadium, parameters);
                log.info("Stadium: {} stored in db.", stadium.getStadium());
            } else if (!checkIfObjectAlreadyExist(SportObjectEnum.STADIUM, stadium.getExternalTeamId(), stadium.getId())) {
                this.namedParameterJdbcTemplate.update(updateStadium, parameters);
                log.info("Stadium: {} now has additional team assigned to with id: {}", stadium.getStadium(), stadium.getExternalTeamId());
            }
        }
    }

    public void persistLeague (List<League> leagues) {
        if (leagues.isEmpty()) {
            return;
        }

        String persistLeague = "INSERT INTO public.leagues (league_id, name, type, logo, year, start, \"end\", country) " +
                "VALUES(:leagueId, :name, :type, :logo, :year, :start, :end, :country)";

        for (League league : leagues) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("leagueId", league.getExternalLeagueId() != null ? league.getExternalLeagueId() : null);
            parameters.addValue("name", league.getName() != null ? league.getName() : null);
            parameters.addValue("type", league.getType() != null ? league.getType() : null);
            parameters.addValue("logo", league.getLogo() != null ? league.getLogo() : null);
            parameters.addValue("year", league.getYear() != null ? league.getYear() : null);
            parameters.addValue("start", league.getStart() != null ? league.getStart() : null);
            parameters.addValue("end", league.getEnd() != null ? league.getEnd() : null);
            parameters.addValue("country", league.getCountry().getName() != null ? league.getCountry().getName() : null);

            if (!checkIfObjectAlreadyExist(SportObjectEnum.LEAGUE, league.getExternalLeagueId(), league.getCountry().getName())) {
                this.namedParameterJdbcTemplate.update(persistLeague, parameters);
                log.info("League: {} stored in db.", league.getName());
            }
        }
    }

    public void persistLeagueCoverage (List<LeagueCoverage> leagueCoverages) {
        if (leagueCoverages.isEmpty()) {
            return;
        }
    }

    public void persistCountry (List<Country> countries) {
        if (countries.isEmpty()) {
            return;
        }
    }


    private boolean checkIfObjectAlreadyExist(SportObjectEnum object, Integer internalId, Object externalId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("internalId", internalId);
        parameters.addValue("externalStadiumId", externalId);
        int queryIndex = (internalId != null && !object.equals(SportObjectEnum.STADIUM)) ? 0 : 1;
        return Boolean.TRUE.equals(
                namedParameterJdbcTemplate.queryForObject(
                        queriesForChecking.get(object).get(queryIndex),
                        parameters,
                        Boolean.class));
    }
}
