package com.mySportPage.service;

import com.mySportPage.model.Coach;
import com.mySportPage.model.request.CoachRequestModel;

import java.util.List;

public interface CoachService {

    List<Coach> getCoachesByLeague (Integer leagueId);

    /**
     * May it looks weird but more than one coach can be hired as main team coach during same time.
     */
    List<Coach> getCoachesByTeam (Integer teamId);

    List<Coach> getCoachesByCountry (String country);

    List<Coach> getCoaches(CoachRequestModel requestModel);
}
