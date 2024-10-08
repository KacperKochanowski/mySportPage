package com.mySportPage.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SportPageBaseResponse {

    private final boolean success;
    private String errorMessage;

    public SportPageBaseResponse() {
        this.success = true;
    }

    public SportPageBaseResponse(boolean success) {
        this.success = success;
    }

    public SportPageBaseResponse(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public SportPageBaseResponse(Exception ex) {
        this.success = false;
        this.errorMessage = ex.getMessage();
    }
}
