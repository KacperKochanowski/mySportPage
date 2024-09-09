package com.mySportPage.client;

import com.google.gson.reflect.TypeToken;
import com.mySportPage.model.Team;
import com.mySportPage.model.response.FeedProviderLeagueResponseModel;
import com.mySportPage.model.response.FeedProviderResponseModel;
import com.mySportPage.rest.AbstractRestService;
import com.mySportPage.rest.path.external.ExternalPaths;
import com.mySportPage.rest.response.SportPageResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;
import static com.mySportPage.rest.path.internal.CommonRestParams.NAME;

@Component
@Scope("singleton")
public class FeedProviderClient extends AbstractRestService {

    public SportPageResponse<List<Team>> getTeamsAndStadiums(Integer leagueId, Integer season) {
        Map<String, String> requestParams = new HashMap<>() {{
            put(LEAGUE, String.valueOf(leagueId));
            put(SEASON, String.valueOf(season));
        }};
        String url = prepareParams(ExternalPaths.GET_TEAMS_AND_STADIUMS_V3.getUrl(), requestParams);
        Type type = new TypeToken<FeedProviderResponseModel<List<Team>>>() {
        }.getType();
        FeedProviderResponseModel<List<Team>> feedResponse = handleResponse(url, type);
        feedResponse.setResponse(fillLeagueId(leagueId, feedResponse));
        return new SportPageResponse<>(feedResponse.getResponse());
    }

    public SportPageResponse<List<FeedProviderLeagueResponseModel>> getLeagues(String leagueId, String season, String code, String country, String name) {
        Map<String, String> requestParams = new HashMap<>() {{
            put(LEAGUE_ID, leagueId);
            put(SEASON, season);
            put(CODE, code);
            put(COUNTRY, country);
            put(NAME, name);
        }};
        String url = prepareParams(ExternalPaths.GET_LEAGUES_V3.getUrl(), requestParams);
        Type type = new TypeToken<FeedProviderResponseModel<List<Team>>>() {
        }.getType();
        FeedProviderResponseModel<List<FeedProviderLeagueResponseModel>> feedResponse = handleResponse(url, type);
        return new SportPageResponse<>(feedResponse.getResponse());
    }

    private List<Team> fillLeagueId(Integer leagueId, FeedProviderResponseModel<List<Team>> feedResponse) {
        return feedResponse.getResponse().stream()
                .peek(team -> team.setLeagueId(leagueId))
                .collect(Collectors.toList());
    }
}
