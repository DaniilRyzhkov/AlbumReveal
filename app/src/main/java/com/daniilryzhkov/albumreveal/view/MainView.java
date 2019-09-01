package com.daniilryzhkov.albumreveal.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.daniilryzhkov.albumreveal.retrofit.ResultModel;

import java.util.List;

/**
 * Interface for MainActivity
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void requestFocus();

    void clearFocus();

    void showProgressBar();

    void hideProgressBar();

    void hideKeyboard();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showToast(String message);

    void updateList(List<ResultModel> results);

}
