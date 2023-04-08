package com.mySportPage.dataAcquisition.rest;

import com.mySportPage.dataAcquisition.model.FixtureDTO;
import com.mySportPage.dataAcquisition.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("fixtures")
public class FixtureRest {

    @Autowired
    FixtureService fixtureService;

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("current")
    public List<FixtureDTO> getFixtures() {
        return fixtureService.getFixtures();
    }
}
