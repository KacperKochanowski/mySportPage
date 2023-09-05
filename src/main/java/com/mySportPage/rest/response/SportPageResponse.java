package com.mySportPage.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.squareup.okhttp.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;


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

    public SportPageResponse(HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
    }

    public SportPageResponse(Response response) {
        this.code = response.code();
        this.message = response.message();
    }
}
