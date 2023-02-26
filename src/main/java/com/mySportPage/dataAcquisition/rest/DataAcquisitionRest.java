package com.mySportPage.dataAcquisition.rest;

import com.mySportPage.dataAcquisition.ExternalPaths;
import com.mySportPage.dataAcquisition.service.DataAcquisitionService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
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
    @PostMapping("/createTeamsAndStadiums")
    public void createTeamsAndStadiums(@RequestParam("leagueId") Integer leagueId, @RequestParam("season") Integer season) {
        String externalPath = ExternalPaths.GET_ALL_TEAMS_FROM_ONE_LEAGUE_ID_V3.getUrl();
        if (leagueId != null) {
            externalPath += "?league=" + leagueId;
        }
        if (season != null) {
            externalPath += leagueId != null ? "&" : "?";
            externalPath += "season=" + season;
        }
        dataAcquisitionService.createTeamsAndStadiums(sendGetRequest(externalPath));
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
