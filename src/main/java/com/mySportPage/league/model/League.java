package com.mySportPage.league.model;

import com.mySportPage.dataAcquisition.model.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class League {

    private Integer externalLeagueId;

    private String name;

    private String type;

    private String logo;

    private Integer year;

    private Date start;

    private Date end;

    private Country country;


    public League(Integer externalLeagueId) {
        this.externalLeagueId = externalLeagueId;
    }
}
