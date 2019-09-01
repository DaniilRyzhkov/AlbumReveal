package com.daniilryzhkov.albumreveal.presenter;

import com.arellomobile.mvp.MvpView;

/**
 * BasePresenter interface for CallbackWrapper abstract class
 */
public interface BasePresenter extends MvpView {

    void startToWork();

    void onError(String message);
}
