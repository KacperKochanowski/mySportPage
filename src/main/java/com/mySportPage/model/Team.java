package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Team {

    @JsonProperty("id")
    private Integer externalTeamId;

    private String name;

    @JsonProperty("code")
    private String shortCut;

    @JsonProperty("logo")
    private String clubCrest;

    private String country;

    @JsonProperty("founded")
    private Integer clubFounded;

    @JsonIgnore
    private boolean national;

}
