package com.mySportPage.dataAcquisition.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mySportPage.dataAcquisition.dao.DataAcquisitionDao;
import com.mySportPage.model.Stadium;
import com.mySportPage.model.Team;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
}
