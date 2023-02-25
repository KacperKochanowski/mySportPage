package com.mySportPage.dataAcquisition.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mySportPage.dataAcquisition.dao.DataAcquisitionDao;
import com.mySportPage.model.Stadium;
import com.mySportPage.model.Team;
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

    private static final Map<Object, List<Object>> dataFromJson = new HashMap<>();


    public void createTeamsAndStadiums(String data) {
        if (data != null) {
            dataAcquisitionDao.persistTeamsAndStadiums(mapJSONObjectToCustomList(data));
        }
    }

    public static List<Team> mapJSONObjectToCustomList(String responseBody) {
        JSONObject jsonObject = new JSONObject(responseBody);
        String teams = jsonObject.getJSONObject("api").getJSONArray("teams").toString();
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Team> participantJsonList;
            participantJsonList = mapper.readValue(teams, new TypeReference<>() {
            });
            return participantJsonList;
        } catch (JsonProcessingException ex) {
            log.info("DataAcquisitionService.mapJSONObjectToCustomList(): Couldn't parse data from given json. Message: " + ex.getMessage());
        }
        return new ArrayList<>();
    }

}
