package com.mySportPage.service;

import com.mySportPage.model.*;
import com.mySportPage.rest.response.SportPageResponse;

public interface DataAcquisitionService {

    void createObjects (String data, SportObjectEnum sportObjectEnum);

    <T> void createObjects(SportPageResponse<T> data, SportObjectEnum sportObjectEnum);
}
