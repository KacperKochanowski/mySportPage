package com.mySportPage.model;

import lombok.Data;

import java.util.Date;

@Data
public class League {

    private Integer externalLeagueId;

    private String name;

    private String type;

    private String logo;

    private Integer year;

    private Date start;

    private Date end;

    private Country country;

}
