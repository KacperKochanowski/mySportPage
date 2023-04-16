package com.mySportPage.dataAcquisition.rest;

import com.mySportPage.dataAcquisition.ExternalPaths;
import com.mySportPage.dataAcquisition.model.SportEnum;
import com.mySportPage.dataAcquisition.model.SportObjectEnum;
import com.mySportPage.dataAcquisition.service.DataAcquisitionService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createTeamsAndStadiums/")
    public void createTeamsAndStadiums(@RequestParam("leagueId") Integer leagueId,
                                       @RequestParam("season") Integer season,
                                       @RequestParam("sportId") Integer sportId) {
        String externalPath = ExternalPaths.GET_TEAMS_AND_STADIUMS_V3.getUrl();
        if (leagueId != null) {
            externalPath += "?league=" + leagueId;
        }
        if (season != null) {
            externalPath += leagueId != null ? "&" : "?";
            externalPath += "season=" + season;
        }
        String response = sendGetRequest(externalPath);
        dataAcquisitionService.createObjects(response, SportObjectEnum.STADIUM, SportEnum.getById(sportId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createLeagues")
    public void createLeagues(@RequestParam(required = false) String leagueId,
                              @RequestParam(required = false) String season,
                              @RequestParam(required = false) String code,
                              @RequestParam(required = false) String country,
                              @RequestParam(required = false) String name,
                              @RequestParam("sportId") Integer sportId) {
        String externalPath = ExternalPaths.GET_LEAGUES_V3.getUrl();
        if (leagueId != null) {
            externalPath += externalPath.contains("?") ? "&" : "?";
            externalPath += "league=" + leagueId;
        }
        if (season != null) {
            externalPath += externalPath.contains("?") ? "&" : "?";
            externalPath += "season=" + season;
        }
        if (code != null) {
            externalPath += externalPath.contains("?") ? "&" : "?";
            externalPath += "code=" + code;
        }
        if (country != null) {
            externalPath += externalPath.contains("?") ? "&" : "?";
            externalPath += "country=" + country;
        }
        if (name != null) {
            externalPath += externalPath.contains("?") ? "&" : "?";
            externalPath += "name=" + name;
        }
        String response = sendGetRequest(externalPath);
        dataAcquisitionService.createObjects(response, SportObjectEnum.LEAGUE, SportEnum.getById(sportId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createFixtures")
    public void createLeagues(@RequestParam(required = false) String leagueId,
                              @RequestParam(required = false) Integer season,
                              @RequestParam("sportId") Integer sportId) {
        String externalPath = ExternalPaths.GET_FIXTURES_V3.getUrl();

        if (leagueId != null) {
            externalPath += "?league=" + leagueId;
        }
        if (season != null) {
            externalPath += leagueId != null ? "&" : "?";
            externalPath += "season=" + season;
        }
        String response = sendGetRequest(externalPath);
        dataAcquisitionService.createObjects(response, SportObjectEnum.FIXTURE, SportEnum.getById(sportId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createStandings")
    public void createStandings(@RequestParam(required = false) String leagueId,
                                @RequestParam Integer season,
                                @RequestParam("sportId") Integer sportId) {
        String externalPath = ExternalPaths.GET_STANDINGS_V3.getUrl().replace("{season}", String.valueOf(season));

        if (leagueId != null) {
            externalPath += "&league=" + leagueId;
        }
        String response = sendGetRequest(externalPath);
        dataAcquisitionService.createObjects(response, SportObjectEnum.STANDING, SportEnum.getById(sportId));
    }

    public String sendGetRequest(String path) {
        if (path != null && !path.trim().isEmpty()) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path)
                        .get()
                        .addHeader("X-RapidAPI-Key", X_RAPID_API_KEY)
                        .addHeader("X-RapidAPI-Host", X_RAPID_API_HOST)
                        .build();
                return client.newCall(request).execute().body().string();
            } catch (IOException ex) {
                log.info("DataAcquisitionRest.sendGetRequest(): Couldn't fetch data from request. Message: " + ex.getMessage());
            }
        }
        return null;
    }
}
