package com.mySportPage.dataAcquisition.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mySportPage.dataAcquisition.dao.DataAcquisitionDao;
import com.mySportPage.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataAcquisitionService {

    @Autowired
    private DataAcquisitionDao dataAcquisitionDao;

    private static final Logger log = LoggerFactory.getLogger(DataAcquisitionService.class);

    public void createTeamsAndStadiums(String data) {
        if (data != null) {
            dataAcquisitionDao.persistTeams(mapJSONObjectToTeamsList(data));
            dataAcquisitionDao.persistStadiums(mapJSONObjectToStadiumsList(data));
        }
    }

    public void createLeagues(String data) {
        if (data != null) {
            dataAcquisitionDao.persistLeague(mapJSONObjectToLeaguesList(data));
            dataAcquisitionDao.persistLeagueCoverage(mapJSONObjectToLeagueCoveragesList(data));
            dataAcquisitionDao.persistCountry(mapJSONObjectToCountriesList(data));
        }
    }


    public List<Team> mapJSONObjectToTeamsList(String responseBody) {
        try {
            List<Team> teams = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray response = new JSONObject(responseBody).getJSONArray("response");
            for (int i = 0; i < response.length(); i++) {
                teams.add(objectMapper.readValue(response.getJSONObject(i).getJSONObject("team").toString(), Team.class));
            }
            return teams;
        } catch (JsonProcessingException e) {
            log.error("DataAcquisitionService.mapJSONObjectToTeamsList(): Couldn't parse data from given json. Message: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Country> mapJSONObjectToCountriesList(String responseBody) {
        try {
            List<Country> countries = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray response = new JSONObject(responseBody).getJSONArray("response");
            Country country;
            for (int i = 0; i < response.length(); i++) {
                country = objectMapper.readValue(response.getJSONObject(i).getJSONObject("country").toString(), Country.class);
                if (country != null && !countries.stream().map(Country::getName).toList().contains(country.getName())) {
                    countries.add(country);
                }
            }
            return countries;
        } catch (JsonProcessingException e) {
            log.error("DataAcquisitionService.mapJSONObjectToCountriesList(): Couldn't parse data from given json. Message: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Stadium> mapJSONObjectToStadiumsList(String responseBody) {
        List<Stadium> stadiums = new ArrayList<>();
        JSONArray response = new JSONObject(responseBody).getJSONArray("response");
        for (int i = 0; i < response.length(); i++) {
            JSONObject element = response.getJSONObject(i);
            Stadium stadium = new Stadium();
            stadium.setExternalTeamId(element.getJSONObject("team").getInt("id"));
            element = response.getJSONObject(i).getJSONObject("venue");
            stadium.setId(element.getInt("id"));
            stadium.setStadium(element.getString("name"));
            stadium.setAddress(element.getString("address"));
            stadium.setCity(element.getString("city"));
            stadium.setCapacity(element.getLong("capacity"));
            stadiums.add(stadium);
        }
        return stadiums;
    }

    public List<League> mapJSONObjectToLeaguesList(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<League> leagues = new ArrayList<>();
        JSONArray response = new JSONObject(responseBody).getJSONArray("response");
        for (int i = 0; i < response.length(); i++) {
            JSONObject element = response.getJSONObject(i).getJSONObject("league");
            League league = new League();
            league.setExternalLeagueId(element.getInt("id"));
            league.setName(element.getString("name"));
            league.setType(element.getString("type"));
            league.setLogo(element.getString("logo"));
            element = response.getJSONObject(i).getJSONArray("seasons").getJSONObject(0);
            league.setYear(element.getInt("year"));
            try {
                league.setStart(format.parse(element.getString("start")));
                league.setEnd(format.parse(element.getString("end")));
                league.setCountry(objectMapper.readValue(response.getJSONObject(i).getJSONObject("country").toString(), Country.class));
            } catch (ParseException pex) {
                log.error("DataAcquisitionService.mapJSONObjectToLeaguesList():Couldn't parse start or end date of league with id = " + league.getExternalLeagueId());
            }
            catch (JsonProcessingException e) {
                log.error("DataAcquisitionService.mapJSONObjectToLeaguesList():Couldn't assign country to league = " + league.getExternalLeagueId());
                e.printStackTrace();
            }
            leagues.add(league);
        }
        return leagues;
    }

    public List<LeagueCoverage> mapJSONObjectToLeagueCoveragesList(String responseBody) {
        List<LeagueCoverage> leagueCoverages = new ArrayList<>();
        JSONArray response = new JSONObject(responseBody).getJSONArray("response");
        for (int i = 0; i < response.length(); i++) {
            JSONObject element = response.getJSONObject(i).getJSONObject("league");
            LeagueCoverage leagueCoverage = new LeagueCoverage();
            leagueCoverage.setExternalLeagueId(element.getInt("id"));
            element = response.getJSONObject(i).getJSONArray("seasons").getJSONObject(0).getJSONObject("coverage").getJSONObject("fixtures");
            leagueCoverage.setEvents(element.getBoolean("events"));
            leagueCoverage.setLineups(element.getBoolean("lineups"));
            leagueCoverage.setStatisticsFixtures(element.getBoolean("statistics_fixtures"));
            leagueCoverage.setStatisticsPlayers(element.getBoolean("statistics_players"));
            element = response.getJSONObject(i).getJSONArray("seasons").getJSONObject(0).getJSONObject("coverage");
            leagueCoverage.setStandings(element.getBoolean("standings"));
            leagueCoverage.setPlayers(element.getBoolean("players"));
            leagueCoverage.setTopScorers(element.getBoolean("top_scorers"));
            leagueCoverage.setTopAssists(element.getBoolean("top_assists"));
            leagueCoverage.setTopCards(element.getBoolean("top_cards"));
            leagueCoverage.setInjuries(element.getBoolean("injuries"));
            leagueCoverage.setPredictions(element.getBoolean("predictions"));
            leagueCoverage.setOdds(element.getBoolean("odds"));
            leagueCoverages.add(leagueCoverage);
        }
        return leagueCoverages;
    }
}
