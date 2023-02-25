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

    private String country;

    @JsonProperty("is_national")
    private boolean isNational;

    @JsonProperty("founded")
    private Integer clubFounded;


    @JsonProperty("venue_name")
    private String stadium;

    @JsonProperty(value = "venue_surface")
    private String venueSurface;

    @JsonProperty("venue_address")
    private String addressOfStadium;

    @JsonProperty("venue_city")
    private String cityOfStadium;

    @JsonProperty("venue_capacity")
    private Long capacity;


}
