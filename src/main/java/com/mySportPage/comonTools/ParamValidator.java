package com.mySportPage.comonTools;

public class ParamValidator {

    public static void validateParam(String param) {
        if (param.isEmpty() || param.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid param value!");
        }
    }
}
