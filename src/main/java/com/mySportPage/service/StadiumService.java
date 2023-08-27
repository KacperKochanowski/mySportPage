package com.mySportPage.service;

import com.mySportPage.model.dto.StadiumDTO;

import java.util.List;

public interface StadiumService {

    List<StadiumDTO> getByCity(String city);

    List<StadiumDTO> getByAddress(String address);

    List<StadiumDTO> getByTeamId(Integer teamId);

    List<StadiumDTO> getByTeamName(String teamName);

    List<StadiumDTO> getByName(String name);
}
