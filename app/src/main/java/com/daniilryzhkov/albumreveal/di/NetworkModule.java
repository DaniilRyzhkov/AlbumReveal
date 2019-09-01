package com.daniilryzhkov.albumreveal.di;

import com.daniilryzhkov.albumreveal.retrofit.ITunesApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DI module for provide Retrofit dependency
 */
@Module
public class NetworkModule {
    private static final String BASE_URL = "https://itunes.apple.com/";
    private ITunesApi api;

    public NetworkModule() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(ITunesApi.class);
    }

    @Provides
    @Singleton
    ITunesApi provideITunesApi() {
        return api;
    }
}
