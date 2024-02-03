package com.mySportPage.exception;

import java.io.Serial;

public class MissingMandatoryParamException extends SportPageException {

    @Serial
    private static final long serialVersionUID = 54234131236623412L;

    public MissingMandatoryParamException(String details) {
        super(SportPageMessageKey.MANDATORY_VALUE_MISSING, details);
    }
}
