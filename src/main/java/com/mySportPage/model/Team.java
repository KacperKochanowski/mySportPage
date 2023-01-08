package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Team {
    @JsonProperty("team_id")
    private Integer externalTeamId;
    private String name;
    @JsonProperty("code")
    private String shortCut;
    @JsonProperty("logo")
    private String clubCrest;
}
