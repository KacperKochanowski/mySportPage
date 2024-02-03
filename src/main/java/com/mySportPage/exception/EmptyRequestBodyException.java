package com.mySportPage.exception;

import java.io.Serial;

public class EmptyRequestBodyException extends SportPageException {

    @Serial
    private static final long serialVersionUID = 54212126762310055L;

    public EmptyRequestBodyException() {
        super(SportPageMessageKey.EMPTY_REQUEST_BODY);
    }
}
