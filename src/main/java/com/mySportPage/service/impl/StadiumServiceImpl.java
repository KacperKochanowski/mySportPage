package com.mySportPage.service.impl;

import com.mySportPage.dao.StadiumDao;
import com.mySportPage.model.Stadium;
import com.mySportPage.model.dto.StadiumDTO;
import com.mySportPage.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StadiumServiceImpl implements StadiumService {

    @Autowired
    private StadiumDao stadiumDao;

    @Override
    public List<StadiumDTO> getByCity(String city) {
        List<Stadium> stadiums = stadiumDao.getByCity(city);
        return mapToDTO(stadiums);
    }

    @Override
    public List<StadiumDTO> getByAddress(String address) {
        List<Stadium> stadiums = stadiumDao.getByAddress(address);
        return mapToDTO(stadiums);
    }

    @Override
    public List<StadiumDTO> getByTeamId(Integer id) {
        List<Stadium> stadiums = stadiumDao.getByTeamId(id);
        return mapToDTO(stadiums);
    }

    @Override
    public List<StadiumDTO> getByTeamName(String teamName) {
        List<Stadium> stadiums = stadiumDao.getByTeamName(teamName);
        return mapToDTO(stadiums);
    }

    @Override
    public List<StadiumDTO> getByName(String name) {
        List<Stadium> stadiums = stadiumDao.getByName(name);
        return mapToDTO(stadiums);
    }

    private List<StadiumDTO> mapToDTO(List<Stadium> stadiums) {
        List<StadiumDTO> stadiumDTOS = new ArrayList<>();
        for (Stadium stadium : stadiums) {
            stadiumDTOS.add(StadiumDTO
                    .builder()
                    .withStadium(stadium.getStadium())
                    .withAddress(stadium.getAddress())
                    .withCapacity(stadium.getCapacity())
                    .withCity(stadium.getCity())
                    .build());
        }
        return stadiumDTOS;
    }
}
