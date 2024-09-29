package com.mySportPage.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class CoachRequestModel {

    private Integer leagueId;
    private Integer teamId;
    private String country;
}
