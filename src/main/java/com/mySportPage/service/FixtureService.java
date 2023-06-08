package com.mySportPage.service;

import com.mySportPage.model.SportEnum;
import com.mySportPage.model.dto.FixtureDTO;

import java.util.List;
import java.util.Map;

public interface FixtureService {

    List<FixtureDTO> getFixtures(SportEnum sport);

    Map<String, List<FixtureDTO>> getFixtures(Integer leagueId, Integer round, SportEnum sport);

    List<FixtureDTO> getFixtures(Integer teamId, String place, SportEnum sport);

    List<FixtureDTO> getFixtures(Integer teamId, boolean played, SportEnum sport);

    Map<String, Map<String, Map<String, List<FixtureDTO>>>> getFixturesByDateLeagueRound(SportEnum sport);
}
