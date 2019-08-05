package com.daniilryzhkov.albumreveal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Response model from iTunes (album name search)
 */
public class AlbumResponse {

    @SerializedName("results")
    @Expose
    private List<Album> results;

    public List<Album> getAlbums() {
        return results;
    }
}
