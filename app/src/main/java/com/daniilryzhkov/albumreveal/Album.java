package com.daniilryzhkov.albumreveal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model of album
 */
public class Album {

    @SerializedName("collectionId")
    @Expose
    private Integer collectionId;

    @SerializedName("artistName")
    @Expose
    private String artistName;

    @SerializedName("collectionName")
    @Expose
    private String collectionName;

    @SerializedName("artworkUrl100")
    @Expose
    private String artworkUrl100;

    public Integer getCollectionId() {
        return collectionId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }
}