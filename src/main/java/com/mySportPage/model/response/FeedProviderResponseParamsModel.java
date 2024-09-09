package com.mySportPage.model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class FeedProviderResponseParamsModel {

    @SerializedName("league")
    private Integer leagueId;
    private Integer season;
    @SerializedName("code")
    private String countryCode;
    @SerializedName("fixture")
    private Integer fixtureId;
    @SerializedName("team")
    private Integer teamId;
}
