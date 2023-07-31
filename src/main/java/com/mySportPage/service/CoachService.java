package com.mySportPage.service;

import com.mySportPage.model.Coach;
import com.mySportPage.model.SportEnum;

import java.util.List;

public interface CoachService {

    List<Coach> getCoachesByLeague (Integer leagueId, SportEnum sport);
}
