package com.mySportPage.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class League {

    @SerializedName("id")
    private Integer externalLeagueId;

    private String name;

    private String type;

    private String logo;

    private Country country;


    public League(Integer externalLeagueId) {
        this.externalLeagueId = externalLeagueId;
    }
}
