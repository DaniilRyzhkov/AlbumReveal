package com.daniilryzhkov.albumreveal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Track list model including collection info
 */
public class Track {

    @SerializedName("artistName")
    @Expose
    private String artistName;

    @SerializedName("collectionName")
    @Expose
    private String collectionName;

    @SerializedName("trackName")
    @Expose
    private String trackName;

    @SerializedName("artworkUrl100")
    @Expose
    private String artworkUrl100;

    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;

    @SerializedName("trackCount")
    @Expose
    private Integer trackCount;

    @SerializedName("trackNumber")
    @Expose
    private Integer trackNumber;

    @SerializedName("trackTimeMillis")
    @Expose
    private Integer trackTimeMillis;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("primaryGenreName")
    @Expose
    private String primaryGenreName;

    @SerializedName("copyright")
    @Expose
    private String copyright;

    public String getArtistName() {
        return artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer getTrackCount() {
        return trackCount;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public Integer getTrackTimeMillis() {
        return trackTimeMillis;
    }

    public String getCurrency() {
        return currency;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public String getCopyright() {
        return copyright;
    }
}

