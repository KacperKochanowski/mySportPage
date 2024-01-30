package com.mySportPage.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoachRequestModel {

    private Integer leagueId;
    private Integer teamId;
    private String country;
}
