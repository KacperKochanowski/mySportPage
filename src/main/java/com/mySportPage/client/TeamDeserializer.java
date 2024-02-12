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
                .withExternalTeamId(getIntValue(teamJSON, "id"))
                .withCountry(getStringValue(teamJSON, "country"))
                .withName(getStringValue(teamJSON, "name"))
                .withClubCrest(getStringValue(teamJSON, "logo"))
                .withShortCut(getStringValue(teamJSON, "code"))
                .withClubFounded(getIntValue(teamJSON, "founded"))
                .withNational(getBooleanValue(teamJSON, "national"))
                .build();

        JsonObject stadiumJSON = teamJsonObject.getAsJsonObject("venue");
        team.setStadium(Stadium.builder()
                .withId(getIntValue(stadiumJSON, "id"))
                .withName(getStringValue(stadiumJSON, "name"))
                .withAddress(getStringValue(stadiumJSON, "address"))
                .withCity(getStringValue(stadiumJSON, "city"))
                .withCapacity(getIntValue(stadiumJSON, "capacity"))
                .withExternalTeamId(new Integer[team.getExternalTeamId()])
                .build());
        return team;
    }

    private Integer getIntValue(JsonObject jsonObject, String key) {
        JsonElement jsonElement = jsonObject.get(key);
        return jsonElement != null && !jsonElement.isJsonNull() ? jsonElement.getAsInt() : null;
    }

    private String getStringValue(JsonObject jsonObject, String key) {
        JsonElement jsonElement = jsonObject.get(key);
        return jsonElement != null && !jsonElement.isJsonNull() ? jsonElement.getAsString() : null;
    }

    private boolean getBooleanValue(JsonObject jsonObject, String key) {
        JsonElement jsonElement = jsonObject.get(key);
        return jsonElement != null && !jsonElement.isJsonNull() && jsonElement.getAsBoolean();
    }
}
