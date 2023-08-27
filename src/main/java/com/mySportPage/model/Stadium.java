package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
public class Stadium {

    public Stadium(Integer id, String stadium, Integer capacity, String address, String city) {
        this.id = id;
        this.stadium = stadium;
        this.capacity = capacity;
        this.address = address;
        this.city = city;
    }

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("venue_name")
    private String stadium;

    @JsonProperty("venue_capacity")
    private Integer capacity;

    @JsonProperty("venue_address")
    private String address;

    @JsonProperty("venue_city")
    private String city;

    @JsonIgnore
    private Integer[] externalTeamId;
}
