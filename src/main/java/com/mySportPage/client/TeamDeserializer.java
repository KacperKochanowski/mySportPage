package com.mySportPage.client;

import com.google.gson.*;
import com.mySportPage.model.Stadium;
import com.mySportPage.model.Team;

import java.lang.reflect.Type;


public class TeamDeserializer implements JsonDeserializer<Team> {

    @Override
    public Team deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject teamJsonObject = json.getAsJsonObject();

        JsonObject teamJSON = teamJsonObject.getAsJsonObject("team");
        Team team = Team.builder()
                .withExternalTeamId(teamJSON.get("id").getAsInt())
                .withCountry(teamJSON.get("country").getAsString())
                .withName(teamJSON.get("name").getAsString())
                .withClubCrest(teamJSON.get("logo").getAsString())
                .withShortCut(teamJSON.get("code").getAsString())
                .withClubFounded(teamJSON.get("founded").getAsInt())
                .withNational(teamJSON.get("national").getAsBoolean())
                .build();

        JsonObject stadiumJSON = teamJsonObject.getAsJsonObject("venue");
        team.setStadium(Stadium.builder()
                .withId(stadiumJSON.get("id").getAsInt())
                .withName(stadiumJSON.get("name").getAsString())
                .withAddress(stadiumJSON.get("address").getAsString())
                .withCity(stadiumJSON.get("city").getAsString())
                .withCapacity(stadiumJSON.get("capacity").getAsInt())
                .build());
        return team;
    }
}
