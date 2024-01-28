package com.mySportPage.rest;

import com.mySportPage.controller.DataAcquisitionController;
import com.mySportPage.rest.path.external.ExternalPaths;
import com.mySportPage.model.SportObjectEnum;
import com.mySportPage.rest.response.SportPageResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;
import static com.mySportPage.rest.path.internal.DataAcquisitionRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class DataAcquisitionRestService {

    //TODO: przeniesienie dużej logiki do controllera

    @Value("${request.header.X_RAPID_API_KEY}")
    private String X_RAPID_API_KEY;

    @Value("${request.header.X_RAPID_API_HOST}")
    private String X_RAPID_API_HOST;

    @Autowired
    private DataAcquisitionController controller;

    private static final Logger log = LoggerFactory.getLogger(DataAcquisitionRestService.class);

    @PostMapping(CREATE_TEAMS_AND_STADIUMS)
    public SportPageResponse createTeamsAndStadiums(
            @RequestParam(LEAGUE_ID) Integer leagueId,
            @RequestParam(SEASON) Integer season) {

        Map<String, String> requestParams = new HashMap<>() {{
            put(LEAGUE, String.valueOf(leagueId));
            put(SEASON, String.valueOf(season));
        }};

        String externalPath = prepareParams(ExternalPaths.GET_TEAMS_AND_STADIUMS_V3.getUrl(), requestParams);
        Response response = sendGetRequest(externalPath);
        String responseDate = fetchDataFromResponse(response);
        if (responseDate == null) {
            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        controller.createObjects(responseDate, SportObjectEnum.TEAM);
        controller.createObjects(responseDate, SportObjectEnum.STADIUM);
        return new SportPageResponse(response);
    }

    //TODO: przerobić tutaj parametry na jeden obiekt ala RequestModel
    @PostMapping(CREATE_LEAGUES)
    public SportPageResponse createLeagues(
            @RequestParam(required = false) String leagueId,
            @RequestParam(required = false) String season,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String name) {

        Map<String, String> requestParams = new HashMap<>() {{
            put(LEAGUE_ID, leagueId);
            put(SEASON, season);
            put(CODE, code);
            put(COUNTRY, country);
            put(NAME, name);
        }};

        String externalPath = prepareParams(ExternalPaths.GET_LEAGUES_V3.getUrl(), requestParams);
        Response response = sendGetRequest(externalPath);
        String responseDate = fetchDataFromResponse(response);
        if (responseDate == null) {
            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        controller.createObjects(responseDate, SportObjectEnum.LEAGUE);
        return new SportPageResponse(response);
    }

    @PostMapping(CREATE_FIXTURES)
    public SportPageResponse createFixtures(
            @RequestParam(LEAGUE_ID) String leagueId,
            @RequestParam(SEASON) Integer season) {

        Map<String, String> requestParams = new HashMap<>() {{
            put(LEAGUE, leagueId);
            put(SEASON, String.valueOf(season));
        }};

        String externalPath = prepareParams(ExternalPaths.GET_FIXTURES_V3.getUrl(), requestParams);
        Response response = sendGetRequest(externalPath);
        String responseDate = fetchDataFromResponse(response);
        if (responseDate == null) {
            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        controller.createObjects(responseDate, SportObjectEnum.FIXTURE);
        return new SportPageResponse(response);
    }

    @PostMapping(CREATE_STANDINGS)
    public SportPageResponse createStandings(
            @RequestParam(required = false) String leagueId,
            @RequestParam(SEASON) Integer season) {
        String externalPath = ExternalPaths.GET_STANDINGS_V3.getUrl().replace("{season}", String.valueOf(season));

        if (leagueId != null) {
            externalPath += "&league=" + leagueId;
        }
        Response response = sendGetRequest(externalPath);
        String responseDate = fetchDataFromResponse(response);
        if (responseDate == null) {
            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        controller.createObjects(responseDate, SportObjectEnum.STANDING);
        return new SportPageResponse(response);
    }

    @PostMapping(CREATE_FIXTURE_STATISTICS)
    public SportPageResponse createFixtureStatistics(
            @RequestParam(FIXTURE) Integer fixture,
            @RequestParam(required = false) Integer team) {

        Map<String, String> requestParams = new HashMap<>() {{
            put(FIXTURE, String.valueOf(fixture));
            if (team != null) {
                put(TEAM, String.valueOf(team));
            }
        }};

        String externalPath = prepareParams(ExternalPaths.GET_FIXTURES_STATISTICS_V3.getUrl(), requestParams);
        Response response = sendGetRequest(externalPath);
        String responseDate = fetchDataFromResponse(response);
        if (responseDate == null) {
            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        controller.createObjects(responseDate, SportObjectEnum.FIXTURE_STATS);
        return new SportPageResponse(response);
    }

    @PostMapping(CREATE_COACH_WITH_HISTORY)
    public SportPageResponse createCoachWithHistory(
            @PathVariable(TEAM_ID) Integer teamId) {

        String externalPath = prepareParams(ExternalPaths.GET_COACH_WITH_HISTORY_V3.getUrl(), new HashMap<>() {{
            put(TEAM, String.valueOf(teamId));
        }});
        Response response = sendGetRequest(externalPath);
        String responseDate = fetchDataFromResponse(response);
        if (responseDate == null) {
            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        controller.createObjects(responseDate, SportObjectEnum.COACH);
        controller.createObjects(responseDate, SportObjectEnum.COACH_HISTORY);
        return new SportPageResponse(response);
    }

    @PostMapping(CREATE_COUNTRIES)
    public SportPageResponse createCountries() {
        String externalPath = ExternalPaths.GET_COUNTIES.getUrl();
        Response response = sendGetRequest(externalPath);
        String responseDate = fetchDataFromResponse(response);
        if (responseDate == null) {
            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        controller.createObjects(responseDate, SportObjectEnum.COUNTRY);
        return new SportPageResponse(response);
    }

    private Response sendGetRequest(String path) {
        if (path != null && !path.trim().isEmpty()) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path)
                        .get()
                        .addHeader("X-RapidAPI-Key", X_RAPID_API_KEY)
                        .addHeader("X-RapidAPI-Host", X_RAPID_API_HOST)
                        .build();
                return client.newCall(request).execute();
            } catch (IOException ex) {
                log.info("DataAcquisitionRest.sendGetRequest(): Couldn't fetch data from request. Message: " + ex.getMessage());
            }
        }
        return null;
    }

    private String prepareParams(String basePath, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            if (sb.isEmpty()) {
                sb.append("?").append(entry.getKey()).append("=").append(entry.getValue());
            } else {
                sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return basePath + sb;
    }

    private String fetchDataFromResponse(Response response) {
        try {
            return response.body().string();
        } catch (IOException e) {
            log.error("DataAcquisitionRest.fetchDataFromResponse(): couldn't fetch data from response. Cause:{}. Message: {}.", e.getCause(), e.getMessage());
            return null;
        }
    }
}
