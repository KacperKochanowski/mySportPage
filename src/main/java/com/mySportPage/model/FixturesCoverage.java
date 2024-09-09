package com.mySportPage.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixturesCoverage {

    @SerializedName("events")
    private boolean withEvents;

    @SerializedName("lineups")
    private boolean withLineups;

    @SerializedName("statistics_fixtures")
    private boolean withStatisticsFixtures;

    @SerializedName("statistics_players")
    private boolean withStatisticsPlayers;
}
