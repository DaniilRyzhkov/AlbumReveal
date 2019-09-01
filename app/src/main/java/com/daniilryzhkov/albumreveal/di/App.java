package com.daniilryzhkov.albumreveal.di;

import android.app.Application;

/**
 * DI class to create DaggerAppComponent
 */
public class App extends Application {

    private static AppComponent component;

    public static AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .build();
    }
}
