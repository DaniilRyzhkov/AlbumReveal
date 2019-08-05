package com.daniilryzhkov.albumreveal;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ITunesApi singleton
 */
public class ApiClient {
    private static final String BASE_URL = "https://itunes.apple.com/";
    private static ITunesApi api = null;

    public static ITunesApi getApi() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(ITunesApi.class);
        }
        return api;
    }
}
