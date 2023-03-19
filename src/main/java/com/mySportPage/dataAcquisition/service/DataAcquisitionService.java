package com.mySportPage.dataAcquisition.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mySportPage.dataAcquisition.dao.DataAcquisitionDao;
import com.mySportPage.dataAcquisition.model.*;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DataAcquisitionService {

    @Autowired
    private DataAcquisitionDao dataAcquisitionDao;

    private static final Logger log = LoggerFactory.getLogger(DataAcquisitionService.class);

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public void createFixtures(String data) {
        if (data != null) {
            dataAcquisitionDao.persistFixture(mapJSONObjectToFixturesList(data));
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
            } catch (JsonProcessingException e) {
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
            leagueCoverage.setWithEvents(element.getBoolean("events"));
            leagueCoverage.setWithLineups(element.getBoolean("lineups"));
            leagueCoverage.setWithStatisticsFixtures(element.getBoolean("statistics_fixtures"));
            leagueCoverage.setWithStatisticsPlayers(element.getBoolean("statistics_players"));
            element = response.getJSONObject(i).getJSONArray("seasons").getJSONObject(0).getJSONObject("coverage");
            leagueCoverage.setWithStandings(element.getBoolean("standings"));
            leagueCoverage.setWithPlayers(element.getBoolean("players"));
            leagueCoverage.setWithTopScorers(element.getBoolean("top_scorers"));
            leagueCoverage.setWithTopAssists(element.getBoolean("top_assists"));
            leagueCoverage.setWithTopCards(element.getBoolean("top_cards"));
            leagueCoverage.setWithInjuries(element.getBoolean("injuries"));
            leagueCoverage.setWithPredictions(element.getBoolean("predictions"));
            leagueCoverage.setWithOdds(element.getBoolean("odds"));
            leagueCoverages.add(leagueCoverage);
        }
        return leagueCoverages;
    }

    public List<Fixture> mapJSONObjectToFixturesList(String responseBody) {
        Integer leagueId;
        Integer season;;
        List<Fixture> fixtures = new ArrayList<>();
        JSONObject params = new JSONObject(responseBody).getJSONObject("parameters");
        JSONArray response = new JSONObject(responseBody).getJSONArray("response");
        if (params.get("league") != null) {
            leagueId = Integer.parseInt((String) params.get("league"));
        } else {
            leagueId = null;
        }
        if (params.get("season") != null) {
            season = Integer.parseInt((String) params.get("season"));
        } else {
            season = null;
        }
        for (int i = 0; i < response.length(); i++) {
            JSONObject element = response.getJSONObject(i).getJSONObject("fixture");
            Fixture fixture = new Fixture();
            fixture.setLeagueId(leagueId);
            fixture.setSeason(season);
            fixture.setId(element.getInt("id"));
            fixture.setReferee(new Referee(
                    element.get("referee") != null && !(element.get("referee").toString()).equals("null") ?
                            element.getString("referee") : null));
            setFixtureStartDate(fixture, element.getString("date"));
            fixture.setStadiumId(element.getJSONObject("venue").get("id") != null && !(element.getJSONObject("venue").get("id").toString()).equals("null") ?
                    element.getJSONObject("venue").getInt("id") : null);
            fixture.setFinished((element.getJSONObject("status").getString("short")).equals("FT"));
            fixture.setRound(response.getJSONObject(i).getJSONObject("league").getString("round"));
            element = response.getJSONObject(i).getJSONObject("teams").getJSONObject("home");
            fixture.setHost(new Team(element.getInt("id"), element.getString("name")));
            if (element.get("winner") != null &&
                    !(element.get("winner").toString()).equals("null") &&
                    element.getBoolean("winner")) {
                fixture.setWinner(fixture.getHost().getName());
            }
            element = response.getJSONObject(i).getJSONObject("teams").getJSONObject("away");
            fixture.setGuest(new Team(element.getInt("id"), element.getString("name")));
            if (element.get("winner") != null &&
                    !(element.get("winner").toString()).equals("null") &&
                    element.getBoolean("winner")) {
                fixture.setWinner(fixture.getGuest().getName());
            }
            fixture.setEvent(fixture.getHost().getName(), fixture.getGuest().getName());
            element = response.getJSONObject(i).getJSONObject("score").getJSONObject("halftime");
            fixture.setHalftimeScore(Stream.of(new Object[][]{
                    {"HOST", element.get("home") != null && !(element.get("home").toString()).equals("null") ?
                            element.getInt("home") : 0},
                    {"GUEST", element.get("away") != null && !(element.get("away").toString()).equals("null") ?
                            element.getInt("away") : 0},
            }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1])));
            element = response.getJSONObject(i).getJSONObject("score").getJSONObject("fulltime");
            fixture.setFulltimeScore(Stream.of(new Object[][]{
                    {"HOST", element.get("home") != null && !(element.get("home").toString()).equals("null") ?
                            element.getInt("home") : 0},
                    {"GUEST", element.get("away") != null && !(element.get("away").toString()).equals("null") ?
                            element.getInt("away") : 0},
            }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1])));
            if(fixture.getWinner() == null) {
                fixture.setWinner("-");
            }
            fixtures.add(fixture);
        }
        return fixtures;
    }

    private void setFixtureStartDate(Fixture fixture, String date) {
        try {
            if (date.contains("+")) {
                date = date.substring(0, date.lastIndexOf("+"));
            }
            fixture.setStart(format.parse(date.replace("T", " ")));
        } catch (ParseException e) {
            log.error("DataAcquisitionService.setFixtureStartDate(): Couldn't parse event start for fixture id: {}", fixture.getId(), e);
        }
    }
}
