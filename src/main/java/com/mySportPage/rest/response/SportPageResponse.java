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


//    public SportPageResponse(int code, String message) {
//        this.code = code;
//        this.message = message;
//    }
//
//    public SportPageResponse(HttpStatus httpStatus) {
//        this.code = httpStatus.value();
//        this.message = httpStatus.getReasonPhrase();
//    }
//
//    public SportPageResponse(Response response) {
//        this.code = response.code();
//        this.message = response.message();
//    }


}
