package com.mySportPage.dataAcquisition;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mySportPage.SensitiveData;
import com.mySportPage.model.Team;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAcquisition {

    private static final Logger log = LoggerFactory.getLogger(DataAcquisition.class);

    private List<Team> mapJSONObjectToList(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        String teams = jsonObject.getJSONObject("api").getJSONArray("teams").toString();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Team> participantJsonList;
            participantJsonList = mapper.readValue(teams, new TypeReference<>() {
            });
            return participantJsonList;
        } catch (JsonProcessingException ex) {
            log.info("Couldn't parse data from given json. Message: " + ex.getMessage());
        }
        return new ArrayList<>();
    }

    private String sendGetRequest(String path) {
        if (path != null && !path.trim().isEmpty()) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path)
                        .get()
                        .addHeader("X-RapidAPI-Key", SensitiveData.X_RAPID_API_KEY.getValue())
                        .addHeader("X-RapidAPI-Host", SensitiveData.X_RAPID_API_HOST.getValue())
                        .build();
                return client.newCall(request).execute().body().string();
            } catch (IOException ex) {
                log.info("Couldn't fetch data from request. Message: " + ex.getMessage());
            }
        }
        return null;
    }
}