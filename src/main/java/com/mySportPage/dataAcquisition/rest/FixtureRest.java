package com.mySportPage.dataAcquisition.rest;

import com.mySportPage.dataAcquisition.model.Fixture;
import com.mySportPage.dataAcquisition.service.FixtureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("fixtures")
public class FixtureRest {

    @Autowired
    FixtureService fixtureService;

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("current")
    public List<Fixture> getFixtures() {
        return fixtureService.getFixtures();
    }
}
