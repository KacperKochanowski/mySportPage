package com.mySportPage.model;

import com.mySportPage.model.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
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
