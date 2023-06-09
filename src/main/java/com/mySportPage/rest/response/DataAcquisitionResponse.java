package com.mySportPage.rest.response;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class DataAcquisitionResponse extends BaseSportPageResponse{

    public DataAcquisitionResponse(int code, String message) {
        super(code, message);
    }
}
