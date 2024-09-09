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
public class LeagueCoverage {

    private Integer externalLeagueId;

    @SerializedName("standings")
    private boolean withStandings;

    @SerializedName("players")
    private boolean withPlayers;

    @SerializedName("top_scorers")
    private boolean withTopScorers;

    @SerializedName("top_assists")
    private boolean withTopAssists;

    @SerializedName("top_cards")
    private boolean withTopCards;

    @SerializedName("injuries")
    private boolean withInjuries;

    @SerializedName("predictions")
    private boolean withPredictions;

    @SerializedName("odds")
    private boolean withOdds;
}
