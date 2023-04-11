package com.mySportPage.dataAcquisition.model;

import com.mySportPage.team.model.Team;
import lombok.Data;

import java.util.Map;

@Data
public class Results {

    private Team team;

    private Integer roundsPlayed;

    private Integer wins;

    private Integer draws;

    private Integer loses;

    private Map<String, Integer> goals;

    private String description;
}
