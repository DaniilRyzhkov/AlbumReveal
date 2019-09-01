package com.daniilryzhkov.albumreveal.retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface for Retrofit to create ResponseModel object
 */
public interface ITunesApi {

    // Search album by title
    @GET("/search?media=music&entity=album")
    Observable<ResponseModel> getAlbumsResponse(@Query("term") String term);

    // Search for a song by collection id
    @GET("/lookup?entity=song")
    Observable<ResponseModel> getTracksResponse(@Query("id") int id);
}
