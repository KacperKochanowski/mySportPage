package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Stadium {

    @JsonProperty("venue_name")
    private String stadium;

    @JsonProperty("venue_capacity")
    private Long capacity;

    @JsonProperty("venue_address")
    private String addressOfStadium;

    @JsonProperty("venue_city")
    private String cityOfStadium;

    @JsonProperty("is_national")
    private boolean isNational;

    @JsonProperty("venue_surface")
    @JsonIgnore
    private String venueSurface;

}
