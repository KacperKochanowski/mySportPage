package com.mySportPage.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mySportPage.model.Team;
import com.mySportPage.model.response.FeedProviderResponseModel;
import com.mySportPage.rest.path.external.ExternalPaths;
import com.mySportPage.rest.response.SportPageResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mySportPage.rest.path.internal.CommonRestParams.LEAGUE;
import static com.mySportPage.rest.path.internal.CommonRestParams.SEASON;

@Component
@Scope("singleton")
public class FeedProviderClient {

    @Value("${request.header.X_RAPID_API_KEY}")
    private static String X_RAPID_API_KEY;

    @Value("${request.header.X_RAPID_API_HOST}")
    private static String X_RAPID_API_HOST;

    private static final Logger log = LoggerFactory.getLogger(FeedProviderClient.class);

    private final Gson gson = new Gson();

    public SportPageResponse<List<Team>> getTeamsAndStadiums(Integer leagueId, Integer season) {
        Map<String, String> requestParams = new HashMap<>() {{
            put(LEAGUE, String.valueOf(leagueId));
            put(SEASON, String.valueOf(season));
        }};
        String url = prepareParams(ExternalPaths.GET_TEAMS_AND_STADIUMS_V3.getUrl(), requestParams);
        Type type = new TypeToken<FeedProviderResponseModel<List<Team>>>() {
        }.getType();
        FeedProviderResponseModel<List<Team>> feedResponse = handleResponse(url, type);
        feedResponse.setResponse(fillAdditionalDataForTeamAndStadium(leagueId, feedResponse.getResponse()));
        return new SportPageResponse<>(feedResponse.getResponse());
    }

    private <T> FeedProviderResponseModel<T> handleResponse(String url, Type type) {
        String response = sendGetRequest(url);
        return gson.fromJson(response, type);
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
                sb.append("?");
            } else {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return basePath + sb;
    }

    private String sendGetRequest(String path) {
        if (path != null && !path.trim().isEmpty()) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path)
                        .get()
                        .addHeader("X-RapidAPI-Key", X_RAPID_API_KEY)
                        .addHeader("X-RapidAPI-Host", X_RAPID_API_HOST)
                        .build();
                return fetchDataFromResponse(client.newCall(request).execute());
            } catch (IOException ex) {
                log.info("FeedProviderClient.sendGetRequest(): Couldn't fetch data from request {}. Message: {}", path, ex.getMessage());
            }
        }
        return null;
    }

    private String fetchDataFromResponse(Response response) {
        try {
            return response.body().string();
        } catch (IOException e) {
            log.error("DataAcquisitionRest.fetchDataFromResponse(): couldn't fetch data from response. Cause:{}. Message: {}.", e.getCause(), e.getMessage());
            return null;
        }
    }


    private List<Team> fillAdditionalDataForTeamAndStadium(Integer leagueId, List<Team> teams) {
        return teams.stream()
                .peek(team -> {
                    team.setLeagueId(leagueId);
                    team.getStadium().setExternalTeamId(new Integer[team.getExternalTeamId()]);
                })
                .collect(Collectors.toList());
    }
}
