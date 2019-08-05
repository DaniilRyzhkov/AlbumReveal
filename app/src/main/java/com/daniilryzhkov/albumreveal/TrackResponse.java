package com.daniilryzhkov.albumreveal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response model from iTunes (collection's id search)
 */
public class TrackResponse {

    @SerializedName("results")
    @Expose
    private List<Track> results;

    public List<Track> getTracks() {
        return results;
    }

}
