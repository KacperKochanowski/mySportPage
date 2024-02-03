package com.mySportPage.rest;

import com.mySportPage.exception.EmptyRequestBodyException;
import com.mySportPage.rest.response.SportPageBaseResponse;
import com.mySportPage.rest.response.SportPageResponse;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractRestService {

    protected <T> SportPageResponse<T> processResponse(Supplier<T> supplier) {
        try {
            T data = supplier.get();
            return new SportPageResponse<>(data);
        } catch (Exception e) {
            return new SportPageResponse<>(e);
        }
    }

    protected SportPageBaseResponse processVoidResponse(Consumer<Void> consumer) {
        try {
            consumer.accept(null);
            return new SportPageBaseResponse(true);
        } catch (Exception e) {
            return new SportPageBaseResponse(e);
        }
    }
}
