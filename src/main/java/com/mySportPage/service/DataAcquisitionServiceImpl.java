package com.mySportPage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mySportPage.dao.DataAcquisitionDao;
import com.mySportPage.model.*;
import com.mySportPage.model.Fixture;
import com.mySportPage.model.League;
import com.mySportPage.model.Team;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DataAcquisitionServiceImpl implements DataAcquisitionService {

    @Autowired
    private DataAcquisitionDao dataAcquisitionDao;

    private static final Logger log = LoggerFactory.getLogger(DataAcquisitionServiceImpl.class);

    private final DateFormat formatWithTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DateFormat formatWithoutTime = new SimpleDateFormat("yyyy-MM-dd");

    private final Map<String, String> incorrectNames = new HashMap<>() {{
        put("Tychy 71", "GKS Tychy");
    }};

    @Override
    public void createObjects(String data, SportObjectEnum sportObjectEnum) {
        if (data != null) {
            switch (sportObjectEnum) {
                case TEAM -> {
                    dataAcquisitionDao.persistTeams(mapJSONObjectToTeamsList(data));
                    dataAcquisitionDao.persistStadiums(mapJSONObjectToStadiumsList(data));
                }
                case LEAGUE -> {
                    dataAcquisitionDao.persistLeague(mapJSONObjectToLeaguesList(data));
                    dataAcquisitionDao.persistLeagueCoverage(mapJSONObjectToLeagueCoveragesList(data));
                    dataAcquisitionDao.persistCountry(mapJSONObjectToCountriesList(data));
                }
                case FIXTURE -> dataAcquisitionDao.persistFixture(mapJSONObjectToFixturesList(data));
                case STANDING -> dataAcquisitionDao.persistStanding(mapJSONObjectToStandingsList(data));
                case FIXTURE_STATS -> dataAcquisitionDao.persistFixtureStats(mapJSONObjectToFixtureStatisticsList(data));
                case COACH -> dataAcquisitionDao.persistCoach(mapJSONObjectToCoachObject(data));
                case COACH_HISTORY -> dataAcquisitionDao.persistCoachCareer(mapJSONObjectToCoachHistoryList(data));
            }
        }
    }

    private List<Team> mapJSONObjectToTeamsList(String responseBody) {
        try {
            int leagueId = Integer.parseInt(new JSONObject(responseBody).getJSONObject("parameters").getString("league"));
            List<Team> teams = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray response = new JSONObject(responseBody).getJSONArray("response");
            for (int i = 0; i < response.length(); i++) {
                Team team = objectMapper.readValue(response.getJSONObject(i).getJSONObject("team").toString(), Team.class);
                team.setName(checkIfTeamNameIsCorrect(team.getName()));
                team.setLeagueId(leagueId);
                teams.add(team);
            }
            return teams;
        } catch (JsonProcessingException e) {
            log.error("DataAcquisitionService.mapJSONObjectToTeamsList(): Couldn't parse data from given json. Message: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    private List<Country> mapJSONObjectToCountriesList(String responseBody) {
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

    private List<Stadium> mapJSONObjectToStadiumsList(String responseBody) {
        List<Stadium> stadiums = new ArrayList<>();
        JSONArray response = new JSONObject(responseBody).getJSONArray("response");
        for (int i = 0; i < response.length(); i++) {
            JSONObject element = response.getJSONObject(i);
            if (!element.getJSONObject("venue").get("id").toString().equals("null") &&
                    !element.getJSONObject("venue").get("city").toString().equals("null")) {
                Stadium stadium = new Stadium();
                stadium.setExternalTeamId(element.getJSONObject("team").getInt("id"));
                element = response.getJSONObject(i).getJSONObject("venue");
                stadium.setId(element.getInt("id"));
                stadium.setStadium(!element.get("name").toString().equals("null") ? element.getString("name") : null);
                stadium.setAddress(!element.get("address").toString().equals("null") ? element.getString("address") : null);
                stadium.setCity(element.getString("city"));
                stadium.setCapacity(!element.get("capacity").toString().equals("null") ? element.getLong("capacity") : null);
                stadiums.add(stadium);
            }
        }
        return stadiums;
    }

    private List<League> mapJSONObjectToLeaguesList(String responseBody) {
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

    private List<LeagueCoverage> mapJSONObjectToLeagueCoveragesList(String responseBody) {
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

    private List<Fixture> mapJSONObjectToFixturesList(String responseBody) {
        Integer leagueId;
        Integer season;
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
            fixture.setStart(parseDate(element.getString("date")));
            fixture.setStadiumId(element.getJSONObject("venue").get("id") != null && !(element.getJSONObject("venue").get("id").toString()).equals("null") ?
                    element.getJSONObject("venue").getInt("id") : null);
            fixture.setFinished((element.getJSONObject("status").getString("short")).equals("FT"));
            fixture.setRound(response.getJSONObject(i).getJSONObject("league").getString("round"));
            element = response.getJSONObject(i).getJSONObject("teams").getJSONObject("home");
            fixture.setHost(new Team(
                    element.getInt("id"),
                    checkIfTeamNameIsCorrect(element.getString("name")))
            );
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
            if (fixture.isFinished()) {
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
            }
            if (fixture.getWinner() == null && fixture.getStart().before(new Date())) {
                fixture.setWinner("-");
            }
            fixtures.add(fixture);
        }
        return fixtures;
    }

    private List<Standing> mapJSONObjectToStandingsList(String responseBody) {
        Integer leagueId;
        Integer season;
        List<Standing> standings = new ArrayList<>();
        JSONObject params = new JSONObject(responseBody).getJSONObject("parameters");
        JSONArray response = new JSONObject(responseBody).getJSONArray("response").getJSONObject(0).getJSONObject("league").getJSONArray("standings").getJSONArray(0);
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
            Standing standing = new Standing();
            standing.setSeason(season);
            standing.setLeague(new League(leagueId));
            JSONObject element = response.getJSONObject(i);
            standing.setRank(element.getInt("rank"));
            standing.setTeam(new Team(
                    element.getJSONObject("team").getInt("id"),
                    checkIfTeamNameIsCorrect(element.getJSONObject("team").getString("name")))
            );
            standing.setPoints(element.getInt("points"));
            standing.setGoalsDiff(String.valueOf(element.getInt("goalsDiff")));
            standing.setForm(element.getString("form"));
            standing.setAdditionalPositionDescription(element.get("description") != null && !(element.get("description").toString()).equals("null") ? element.getString("description") : null);
            standing.setResults(creteResults(element.getJSONObject("all"), "All", standing.getTeam()));
            standing.setHomeResults(creteResults(element.getJSONObject("home"), "Home", standing.getTeam()));
            standing.setAwayResults(creteResults(element.getJSONObject("away"), "Away", standing.getTeam()));
            standing.setUpdated(parseDate(element.getString("update")));
            standings.add(standing);
        }
        return standings;
    }

    private Map<Integer, Map<Integer, FixtureStatistics>> mapJSONObjectToFixtureStatisticsList(String responseBody) {
        Map<Integer, Map<Integer, FixtureStatistics>> fixtureStats = new HashMap<>();
        Map<Integer, FixtureStatistics> fixturesByTeam = new HashMap<>();
        final Integer fixtureId = Integer.valueOf(new JSONObject(responseBody).getJSONObject("parameters").getString("fixture"));
        Integer teamId;
        JSONArray response = new JSONObject(responseBody).getJSONArray("response");

        for (int i = 0; i < response.length(); i++) {
            JSONObject element = response.getJSONObject(i).getJSONObject("team");
            teamId = element.getInt("id");
            JSONArray teamStatsJson = response.getJSONObject(i).getJSONArray("statistics");
            Map<FixtureStatisticsEnum, Object> fixtureStatisticsByTeam = new HashMap<>();
            for (int j = 0; j < teamStatsJson.length(); j++) {
                JSONObject teamStatsJsonElement = teamStatsJson.getJSONObject(j);
                if (FixtureStatisticsEnum.getByDescription(teamStatsJsonElement.getString("type")) != null) {
                    fixtureStatisticsByTeam.put(
                            FixtureStatisticsEnum.getByDescription(teamStatsJsonElement.getString("type")),
                            String.valueOf(teamStatsJsonElement.get("value"))
                    );
                }
            }
            fixturesByTeam.put(teamId, new FixtureStatistics(fixtureStatisticsByTeam));
        }
        fixtureStats.put(fixtureId, fixturesByTeam);
        return fixtureStats;
    }

    private Coach mapJSONObjectToCoachObject(String responseBody) {
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

        JsonArray responseArray = jsonObject.getAsJsonArray("response");

        if (responseArray != null && !responseArray.isJsonNull() && responseArray.size() > 0) {
            JsonObject coachDataObject = responseArray.get(0).getAsJsonObject();

            return Coach.builder()
                    .withExternalId(coachDataObject.getAsJsonPrimitive("id").getAsInt())
                    .withName(coachDataObject.getAsJsonPrimitive("name").getAsString())
                    .withFirstName(coachDataObject.getAsJsonPrimitive("firstname").getAsString())
                    .withLastName(coachDataObject.getAsJsonPrimitive("lastname").getAsString())
                    .withAge(coachDataObject.getAsJsonPrimitive("age").getAsInt())
                    .withBirthDate(parseDate(coachDataObject.getAsJsonObject("birth").getAsJsonPrimitive("date").getAsString()))
                    .withBirthCountry(coachDataObject.getAsJsonObject("birth").getAsJsonPrimitive("country").getAsString())
                    .withNationality(coachDataObject.getAsJsonPrimitive("nationality").getAsString())
                    .withPhoto(coachDataObject.getAsJsonPrimitive("photo").getAsString())
                    .build();
        }
        return null;
    }

    private Map<Integer, List<CoachHistory>> mapJSONObjectToCoachHistoryList(String responseBody) {
        Integer coachId = new JSONObject(responseBody).getJSONArray("response").getJSONObject(0).getInt("id");
        String coachCareer = new JSONObject(responseBody).getJSONArray("response").getJSONObject(0).getJSONArray("career").toString();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Type coachHistoryListType = new TypeToken<List<CoachHistory>>() {}.getType();

        return Map.of(coachId, gson.fromJson(coachCareer, coachHistoryListType));
    }

    private Date parseDate(String date) {
        try {
            if (date.contains("+")) {
                date = date.substring(0, date.lastIndexOf("+"));
            }
            if (date.contains("T")) {
                return formatWithTime.parse(date.replace("T", " "));
            }
            return formatWithoutTime.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    private String parsePlace(JsonObject jsonObject) {
        if (jsonObject.has("place") && !jsonObject.get("place").isJsonNull()) {
            return jsonObject.getAsJsonPrimitive("place").getAsString();
        }
        return null;
    }

    private Results creteResults(JSONObject data, String typeOfResults, Team team) {
        return Results.builder()
                .withDescription(typeOfResults)
                .withTeam(team)
                .withRoundsPlayed(data.getInt("played"))
                .withWins(data.getInt("win"))
                .withDraws(data.getInt("draw"))
                .withLoses(data.getInt("lose"))
                .withGoals(Stream.of(new Object[][]{
                        {"FOR", data.getJSONObject("goals").get("for") != null && !(data.getJSONObject("goals").get("for")).equals("null") ?
                                data.getJSONObject("goals").getInt("for") : 0},
                        {"AGAINST", data.getJSONObject("goals").get("against") != null && !(data.getJSONObject("goals").get("against")).equals("null") ?
                                data.getJSONObject("goals").getInt("against") : 0},
                }).collect(Collectors.toMap(x -> (String) x[0], x -> (Integer) x[1])))
                .build();
    }

    private String checkIfTeamNameIsCorrect(String teamName) {
        return incorrectNames.getOrDefault(teamName, teamName);
    }
}
