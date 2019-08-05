package com.daniilryzhkov.albumreveal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for Retrofit to create AlbumResponse and TrackResponse objects
 */
public interface ITunesApi {

    // Search album's by title
    @GET("/search?media=music&entity=album")
    Call<AlbumResponse> getAlbumsResponse(@Query("term") String term);

    // Browse the album by id
    @GET("/lookup?entity=song")
    Call<TrackResponse> getTracksResponse(@Query("id") int id);
}
