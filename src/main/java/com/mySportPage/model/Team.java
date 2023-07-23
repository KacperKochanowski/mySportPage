package com.mySportPage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Team {

    @SerializedName("id")
    private Integer externalTeamId;

    private String name;

    @SerializedName("code")
    private String shortCut;

    @SerializedName("logo")
    private String clubCrest;

    private String country;

    @SerializedName("founded")
    private Integer clubFounded;

    @JsonIgnore
    private boolean national;


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
