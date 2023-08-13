package com.mySportPage.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder (setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SportPageResponse {

    private int code;
    private String message;
    private Object data;

    public SportPageResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
