package com.mySportPage.service;

import com.mySportPage.model.dto.FixtureDTO;
import com.mySportPage.model.request.FixtureRequestModel;

import java.util.List;
import java.util.Map;

public interface FixtureService {

    List<FixtureDTO> getFixtures();

    Map<String, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round);

    List<FixtureDTO> getFixtures(FixtureRequestModel requestModel);

    Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound();
}
