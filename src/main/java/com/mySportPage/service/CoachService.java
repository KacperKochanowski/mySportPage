package com.mySportPage.service;

import com.mySportPage.model.Coach;
import com.mySportPage.model.SportEnum;

import java.util.List;
import java.util.Map;

public interface CoachService {

    List<Coach> getCoachesByLeague (Integer leagueId, SportEnum sport);

    /**
     * May it looks weird but more than one coach can be hired as main team coach during same time.
     */
    List<Coach> getCoachesByTeam (Integer teamId, SportEnum sport);

    List<Coach> getCoachesByCountry (String countryCode, SportEnum sport);

    List<Coach> getCoaches(Map<String, Object> params, SportEnum sport);
}
