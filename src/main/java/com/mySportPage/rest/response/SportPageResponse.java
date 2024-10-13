package com.mySportPage.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SportPageResponse<T> extends SportPageBaseResponse {

    private T data;

    public SportPageResponse(T data) {
        super(true);
        this.data = data;
    }

    public SportPageResponse(Exception ex) {
        super(ex);
    }

    public SportPageResponse(String errorMessage) {
        super(errorMessage);
    }
}
