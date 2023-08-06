package com.mySportPage.service;

import com.mySportPage.model.dto.FixtureDTO;

import java.util.List;
import java.util.Map;

public interface FixtureService {

    List<FixtureDTO> getFixtures();

    Map<String, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round);

    List<FixtureDTO> getFixtures(Integer teamId, String place);

    List<FixtureDTO> getFixtures(Integer teamId, boolean played);

    Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound();
}
