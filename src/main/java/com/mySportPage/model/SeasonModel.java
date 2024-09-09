package com.mySportPage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeasonModel {

    private Integer year;
    private Date start;
    private Date end;
    private LeagueCoverage coverage;
}
