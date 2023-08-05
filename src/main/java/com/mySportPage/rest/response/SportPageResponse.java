package com.mySportPage.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * I have created this custom response class mainly to see how @SuperBuilder works.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder (setterPrefix = "with")
public class SportPageResponse {

    private int code;
    private String message;
    private Object data;

    public SportPageResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
