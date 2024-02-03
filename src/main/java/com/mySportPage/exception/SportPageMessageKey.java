package com.mySportPage.exception;

import lombok.Getter;

@Getter
public enum SportPageMessageKey {
    EMPTY_REQUEST_BODY("empty-request-body"),
    MANDATORY_VALUE_MISSING("mandatory-value-missing");

    private final String key;

    SportPageMessageKey(String key) {
        this.key = key;
    }
}
