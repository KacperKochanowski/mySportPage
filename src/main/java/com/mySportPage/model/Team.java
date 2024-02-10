package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mySportPage.client.TeamDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(setterPrefix = "with")
@JsonAdapter(TeamDeserializer.class)
public class Team {

    /**
     * JsonProperty is used by ObjectMapper,
     * SerializedName is used by Gson
     */
    @JsonProperty("id")
    @SerializedName("id")
    private Integer externalTeamId;

    private String name;

    @JsonProperty("code")
    @SerializedName("code")
    private String shortCut;

    @JsonProperty("logo")
    @SerializedName("logo")
    private String clubCrest;

    private String country;

    @JsonProperty("founded")
    @SerializedName("founded")
    private Integer clubFounded;

    @JsonIgnore
    private boolean national;

    @JsonIgnore
    private Integer leagueId;

    @SerializedName("venue")
    private Stadium stadium;


    /**
     * Used by method DataAcquisitionDao.mapJSONObjectToCoachHistoryList
     */
    public Team(Integer externalTeamId, String name, String clubCrest) {
        this.externalTeamId = externalTeamId;
        this.name = name;
        this.clubCrest = clubCrest;
    }

    public Team(Integer externalTeamId, String name) {
        this.externalTeamId = externalTeamId;
        this.name = name;
    }
}
