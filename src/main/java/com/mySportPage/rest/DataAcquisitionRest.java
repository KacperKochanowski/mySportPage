package com.mySportPage.rest;

import com.mySportPage.ExternalPaths;
import com.mySportPage.model.SportObjectEnum;
import com.mySportPage.rest.response.DataAcquisitionResponse;
import com.mySportPage.service.DataAcquisitionService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("internal/data-acquisition")
public class DataAcquisitionRest {

    @Value("${request.header.X_RAPID_API_KEY}")
    private String X_RAPID_API_KEY;

    @Value("${request.header.X_RAPID_API_HOST}")
    private String X_RAPID_API_HOST;

    @Autowired
    private DataAcquisitionService dataAcquisitionService;

    private static final Logger log = LoggerFactory.getLogger(DataAcquisitionRest.class);

    @PostMapping("/createTeamsAndStadiums")
    public DataAcquisitionResponse createTeamsAndStadiums(@RequestParam("leagueId") Integer leagueId,
                                                          @RequestParam("season") Integer season) throws IOException {

        Map<String, String> requestParams = new HashMap<>() {{
            put("leagueId", String.valueOf(leagueId));
            put("season", String.valueOf(season));
        }};

        String externalPath = prepareParams(ExternalPaths.GET_TEAMS_AND_STADIUMS_V3.getUrl(), requestParams);
        Response response = sendGetRequest(externalPath);
        dataAcquisitionService.createObjects(response.body().string(), SportObjectEnum.TEAM);
        dataAcquisitionService.createObjects(response.body().string(), SportObjectEnum.STADIUM);

        return new DataAcquisitionResponse(response.code(), response.message());
    }

    @PostMapping("/createLeagues")
    public DataAcquisitionResponse createLeagues(@RequestParam(required = false) String leagueId,
                                                 @RequestParam(required = false) String season,
                                                 @RequestParam(required = false) String code,
                                                 @RequestParam(required = false) String country,
                                                 @RequestParam(required = false) String name) throws IOException {

        Map<String, String> requestParams = new HashMap<>() {{
            put("leagueId", leagueId);
            put("season", season);
            put("code", code);
            put("country", country);
            put("name", name);
        }};

        String externalPath = prepareParams(ExternalPaths.GET_LEAGUES_V3.getUrl(), requestParams);
        Response response = sendGetRequest(externalPath);
        dataAcquisitionService.createObjects(response.body().string(), SportObjectEnum.LEAGUE);

        return new DataAcquisitionResponse(response.code(), response.message());
    }

    @PostMapping("/createFixtures")
    public DataAcquisitionResponse createLeagues(@RequestParam(required = false) String leagueId,
                                                 @RequestParam(required = false) Integer season) throws IOException {

        Map<String, String> requestParams = new HashMap<>() {{
            put("league", leagueId);
            put("season", String.valueOf(season));
        }};

        String externalPath = prepareParams(ExternalPaths.GET_FIXTURES_V3.getUrl(), requestParams);
        Response response = sendGetRequest(externalPath);
        dataAcquisitionService.createObjects(response.body().string(), SportObjectEnum.FIXTURE);

        return new DataAcquisitionResponse(response.code(), response.message());
    }

    @PostMapping("/createStandings")
    public DataAcquisitionResponse createStandings(@RequestParam(required = false) String leagueId,
                                                   @RequestParam Integer season) throws IOException {
        String externalPath = ExternalPaths.GET_STANDINGS_V3.getUrl().replace("{season}", String.valueOf(season));

        if (leagueId != null) {
            externalPath += "&league=" + leagueId;
        }
        Response response = sendGetRequest(externalPath);
        dataAcquisitionService.createObjects(response.body().string(), SportObjectEnum.STANDING);

        return new DataAcquisitionResponse(response.code(), response.message());
    }

    @PostMapping("/createFixtureStatistics")
    public DataAcquisitionResponse createFixtureStatistics(@RequestParam Integer fixture,
                                                           @RequestParam(required = false) Integer team) throws IOException {

        Map<String, String> requestParams = new HashMap<>() {{
            put("fixture", String.valueOf(fixture));
            if(team != null) {
                put("team", String.valueOf(team));
            };
        }};

        String externalPath = prepareParams(ExternalPaths.GET_FIXTURES_STATISTICS_V3.getUrl(), requestParams);
        Response response = sendGetRequest(externalPath);

        dataAcquisitionService.createObjects(response.body().string(), SportObjectEnum.FIXTURE_STATS);
        return new DataAcquisitionResponse(response.code(), response.message());
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
}
