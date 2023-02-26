package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Stadium {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("venue_name")
    private String stadium;

    @JsonProperty("venue_capacity")
    private Long capacity;

    @JsonProperty("venue_address")
    private String address;

    @JsonProperty("venue_city")
    private String city;

    @JsonProperty("team_id")
    private Integer externalTeamId;

}
