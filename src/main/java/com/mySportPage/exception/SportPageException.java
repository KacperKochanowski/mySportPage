package com.mySportPage.exception;


import lombok.Getter;

import java.io.Serial;

@Getter
public class SportPageException extends Exception {

    @Serial
    private static final long serialVersionUID = 1231293870921321L;

    private final SportPageMessageKey messageKey;

    private final String details;

    public SportPageException(SportPageMessageKey messageKey, String details) {
        this.messageKey = messageKey;
        this.details = details;
    }

    @Override
    public String getMessage() {
        return messageKey.getKey();
    }
}
