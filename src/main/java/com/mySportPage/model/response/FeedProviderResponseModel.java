package com.mySportPage.model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class FeedProviderResponseModel<T> {

    @SerializedName("get")
    private String entity;
    private FeedProviderResponseParamsModel parameters;
    private String[] errors;
    private Integer results;
    private FeedProviderResponsePagingModel paging;
    private T response;
}
