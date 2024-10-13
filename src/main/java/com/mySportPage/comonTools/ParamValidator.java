package com.mySportPage.comonTools;

import com.mySportPage.exception.EmptyRequestBodyException;

public class ParamValidator {

    public static void validateStringParam(String param) {
        if (param.isEmpty() || param.trim().isEmpty()) {
            throw new IllegalArgumentException("Forwarded param can not contain only white spaces!");
        }
    }

    public static void validateIntegerParam(Integer param) {
        if (param < 0) {
            throw new IllegalArgumentException("Forwarded param can not be negative!");
        }
    }

    public static void validateCustomParam(Object param) {
        if (param == null) {
            throw new EmptyRequestBodyException();
        }
    }
}
