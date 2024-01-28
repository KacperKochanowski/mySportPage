package com.mySportPage.controller;

import com.mySportPage.model.SportObjectEnum;
import com.mySportPage.service.DataAcquisitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DataAcquisitionController {

    private final DataAcquisitionService service;

    @Autowired
    private DataAcquisitionController(DataAcquisitionService service) {
        this.service = service;
    }

    public void createObjects(String data, SportObjectEnum sportObjectEnum) {
        service.createObjects(data, sportObjectEnum);
    }
}
