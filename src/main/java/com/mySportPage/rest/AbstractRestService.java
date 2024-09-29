package com.mySportPage.rest;

import com.google.gson.Gson;
import com.mySportPage.model.response.FeedProviderResponseModel;
import com.mySportPage.rest.response.SportPageBaseResponse;
import com.mySportPage.rest.response.SportPageResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractRestService {

    @Value("${request.header.X_RAPID_API_KEY}")
    private String X_RAPID_API_KEY;

    @Value("${request.header.X_RAPID_API_HOST}")
    private String X_RAPID_API_HOST;

    private static final Logger log = LoggerFactory.getLogger(AbstractRestService.class);

    private static final Gson gson = new Gson();


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

    protected String prepareParams(String basePath, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            if (sb.isEmpty()) {
                sb.append("?");
            } else {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return basePath + sb;
    }

    protected <T> FeedProviderResponseModel<T> handleResponse(String url, Type type) {
        String response = sendGetRequest(url);
        return gson.fromJson(response, type);
    }

    private String sendGetRequest(String path) {
        if (path != null && !path.trim().isEmpty()) {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path)
                        .get()
                        .addHeader("X-RapidAPI-Key", X_RAPID_API_KEY)
                        .addHeader("X-RapidAPI-Host", X_RAPID_API_HOST)
                        .build();
                return fetchDataFromResponse(client.newCall(request).execute());
            } catch (IOException ex) {
                log.error("AbstractRestService.sendGetRequest(): Couldn't fetch data from request {}. Message: {}", path, ex.getMessage());
            }
        }
        return null;
    }

    private String fetchDataFromResponse(Response response) {
        try {
            return response.body().string();
        } catch (IOException e) {
            log.error("AbstractRestService.fetchDataFromResponse(): couldn't fetch data from response. Cause:{}. Message: {}.", e.getCause(), e.getMessage());
            return null;
        }
    }
}
