package com.mySportPage.model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class FeedProviderResponseModel<T> {

    @SerializedName("get")
    private String entity;
    private FeedProviderResponseParams parameters;
    private String[] errors;
    private Integer results;
    private FeedProviderResponsePaging paging;
    private T response;
}
