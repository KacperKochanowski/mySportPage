package com.mySportPage.model.request;

import com.mySportPage.model.FixturePlaceEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FixtureRequestModel {

    private Integer teamId;
    private FixturePlaceEnum place;
    private Boolean played;
}
