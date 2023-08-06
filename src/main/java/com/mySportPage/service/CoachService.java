package com.mySportPage.service;

import com.mySportPage.model.Coach;

import java.util.List;
import java.util.Map;

public interface CoachService {

    List<Coach> getCoachesByLeague (Integer leagueId);

    /**
     * May it looks weird but more than one coach can be hired as main team coach during same time.
     */
    List<Coach> getCoachesByTeam (Integer teamId);

    List<Coach> getCoachesByCountry (String countryCode);

    List<Coach> getCoaches(Map<String, Object> params);
}
