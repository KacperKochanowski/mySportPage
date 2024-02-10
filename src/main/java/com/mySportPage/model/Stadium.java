package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("id")
    @JsonProperty("id")
    private Integer id;

    @SerializedName("name")
    @JsonProperty("venue_name")
    private String name;

    @JsonProperty("venue_capacity")
    private Integer capacity;

    @JsonProperty("venue_address")
    private String address;

    @JsonProperty("venue_city")
    private String city;

    @JsonIgnore
    private Integer[] externalTeamId;
}
