package com.mySportPage.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(setterPrefix = "with")
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FixtureStatisticsResponse extends BaseSportPageResponse {

    private Object fixtureStatistics;

}