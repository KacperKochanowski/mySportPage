package com.mySportPage.controller;

import com.mySportPage.model.SportObjectEnum;
import com.mySportPage.rest.response.SportPageResponse;
import com.mySportPage.service.DataAcquisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DataAcquisitionController {

    private final DataAcquisitionService service;

    @Autowired
    public DataAcquisitionController(DataAcquisitionService service) {
        this.service = service;
    }

    public void createObjects(String data, SportObjectEnum sportObjectEnum) {
        service.createObjects(data, sportObjectEnum);
    }

    public <T> void createObjects(SportPageResponse<T> data, SportObjectEnum sportObjectEnum) {
        service.createObjects(data, sportObjectEnum);
    }
}
