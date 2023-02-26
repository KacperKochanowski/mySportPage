package com.mySportPage.dataAcquisition.service;

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
        JSONObject jsonObject = new JSONObject(responseBody);
        List<Team> teams = new ArrayList<>();
        JSONArray response = jsonObject.getJSONArray("response");
        for (int i = 0; i < response.length(); i++) {
            JSONObject element = response.getJSONObject(i).getJSONObject("team");
            Team team = new Team();
            team.setExternalTeamId(element.getInt("id"));
            team.setName(element.getString("name"));
            team.setShortCut(element.getString("code"));
            team.setCountry(element.getString("country"));
            team.setClubFounded(element.getInt("founded"));
            team.setClubCrest(element.getString("logo"));
            teams.add(team);
        }
        return teams;
    }

    public List<Stadium> mapJSONObjectToStadiumsList(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        List<Stadium> stadiums = new ArrayList<>();
        JSONArray response = jsonObject.getJSONArray("response");
        for (int i = 0; i < response.length(); i++) {
            JSONObject element = response.getJSONObject(i);
            Stadium stadium = new Stadium();
            stadium.setExternalTeamId(element.getJSONObject("team").getInt("id"));
            element = response.getJSONObject(i).getJSONObject("venue");
            stadium.setStadium(element.getString("name"));
            stadium.setAddress(element.getString("address"));
            stadium.setCity(element.getString("city"));
            stadium.setCapacity(element.getLong("capacity"));
            stadiums.add(stadium);
        }
        return stadiums;
    }
}
