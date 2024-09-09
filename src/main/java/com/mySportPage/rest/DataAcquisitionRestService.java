package com.mySportPage.rest;

import com.mySportPage.client.FeedProviderClient;
import com.mySportPage.controller.DataAcquisitionController;
import com.mySportPage.model.SportObjectEnum;
import com.mySportPage.rest.response.SportPageBaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import static com.mySportPage.rest.path.internal.CommonRestParams.*;
import static com.mySportPage.rest.path.internal.DataAcquisitionRestPath.*;

@RestController
@RequestMapping(ROOT_PATH)
public class DataAcquisitionRestService extends AbstractRestService {

    //TODO: gruby refactor tej klasy

    @Value("${request.header.X_RAPID_API_KEY}")
    private String X_RAPID_API_KEY;

    @Value("${request.header.X_RAPID_API_HOST}")
    private String X_RAPID_API_HOST;

    private final DataAcquisitionController controller;

    private final FeedProviderClient feedProviderClient;

    @Autowired
    public DataAcquisitionRestService(DataAcquisitionController controller, FeedProviderClient feedProviderClient) {
        this.controller = controller;
        this.feedProviderClient = feedProviderClient;
    }

    @PostMapping(CREATE_TEAMS_AND_STADIUMS)
    public SportPageBaseResponse createTeamsAndStadiums(
            @RequestParam(LEAGUE_ID) Integer leagueId,
            @RequestParam(SEASON) Integer season) {
        return processVoidResponse((t) -> controller.createObjects(feedProviderClient.getTeamsAndStadiums(leagueId, season), SportObjectEnum.TEAM_AND_STADIUM));
    }

    @PostMapping(CREATE_LEAGUES)
    public SportPageBaseResponse createLeagues(
            @RequestParam(required = false) String leagueId,
            @RequestParam(required = false) String season,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String name) {
        return processVoidResponse((t) -> controller.createObjects(feedProviderClient.getLeagues(leagueId, season, code, country, name), SportObjectEnum.LEAGUE));
    }
//
//    @PostMapping(CREATE_FIXTURES)
//    public SportPageResponse createFixtures(
//            @RequestParam(LEAGUE_ID) String leagueId,
//            @RequestParam(SEASON) Integer season) {
//
//        Map<String, String> requestParams = new HashMap<>() {{
//            put(LEAGUE, leagueId);
//            put(SEASON, String.valueOf(season));
//        }};
//
//        String externalPath = prepareParams(ExternalPaths.GET_FIXTURES_V3.getUrl(), requestParams);
//        Response response = sendGetRequest(externalPath);
//        String responseDate = fetchDataFromResponse(response);
//        if (responseDate == null) {
//            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
//        }
//        controller.createObjects(responseDate, SportObjectEnum.FIXTURE);
//        return new SportPageResponse(response);
//    }
//
//    @PostMapping(CREATE_STANDINGS)
//    public SportPageResponse createStandings(
//            @RequestParam(required = false) String leagueId,
//            @RequestParam(SEASON) Integer season) {
//        String externalPath = ExternalPaths.GET_STANDINGS_V3.getUrl().replace("{season}", String.valueOf(season));
//
//        if (leagueId != null) {
//            externalPath += "&league=" + leagueId;
//        }
//        Response response = sendGetRequest(externalPath);
//        String responseDate = fetchDataFromResponse(response);
//        if (responseDate == null) {
//            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
//        }
//        controller.createObjects(responseDate, SportObjectEnum.STANDING);
//        return new SportPageResponse(response);
//    }
//
//    @PostMapping(CREATE_FIXTURE_STATISTICS)
//    public SportPageResponse createFixtureStatistics(
//            @RequestParam(FIXTURE) Integer fixture,
//            @RequestParam(required = false) Integer team) {
//
//        Map<String, String> requestParams = new HashMap<>() {{
//            put(FIXTURE, String.valueOf(fixture));
//            if (team != null) {
//                put(TEAM, String.valueOf(team));
//            }
//        }};
//
//        String externalPath = prepareParams(ExternalPaths.GET_FIXTURES_STATISTICS_V3.getUrl(), requestParams);
//        Response response = sendGetRequest(externalPath);
//        String responseDate = fetchDataFromResponse(response);
//        if (responseDate == null) {
//            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
//        }
//        controller.createObjects(responseDate, SportObjectEnum.FIXTURE_STATS);
//        return new SportPageResponse(response);
//    }
//
//    @PostMapping(CREATE_COACH_WITH_HISTORY)
//    public SportPageResponse createCoachWithHistory(
//            @PathVariable(TEAM_ID) Integer teamId) {
//
//        String externalPath = prepareParams(ExternalPaths.GET_COACH_WITH_HISTORY_V3.getUrl(), new HashMap<>() {{
//            put(TEAM, String.valueOf(teamId));
//        }});
//        Response response = sendGetRequest(externalPath);
//        String responseDate = fetchDataFromResponse(response);
//        if (responseDate == null) {
//            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
//        }
//        controller.createObjects(responseDate, SportObjectEnum.COACH);
//        controller.createObjects(responseDate, SportObjectEnum.COACH_HISTORY);
//        return new SportPageResponse(response);
//    }
//
//    @PostMapping(CREATE_COUNTRIES)
//    public SportPageResponse createCountries() {
//        String externalPath = ExternalPaths.GET_COUNTIES.getUrl();
//        Response response = sendGetRequest(externalPath);
//        String responseDate = fetchDataFromResponse(response);
//        if (responseDate == null) {
//            return new SportPageResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
//        }
//        controller.createObjects(responseDate, SportObjectEnum.COUNTRY);
//        return new SportPageResponse(response);
//    }
//
}
