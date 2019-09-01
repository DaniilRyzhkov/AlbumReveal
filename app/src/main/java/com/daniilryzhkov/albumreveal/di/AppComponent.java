package com.daniilryzhkov.albumreveal.di;

import com.daniilryzhkov.albumreveal.presenter.DetailPresenter;
import com.daniilryzhkov.albumreveal.presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {NetworkModule.class})
@Singleton
public interface AppComponent {

    void inject(MainPresenter mainPresenter);

    void inject(DetailPresenter detailPresenter);

}
