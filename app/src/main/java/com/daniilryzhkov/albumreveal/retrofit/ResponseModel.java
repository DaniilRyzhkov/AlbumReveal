package com.daniilryzhkov.albumreveal.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response model from iTunes
 */
public class ResponseModel {

    @SerializedName("results")
    @Expose
    private List<ResultModel> results;

    public List<ResultModel> getResults() {
        return results;
    }
}
