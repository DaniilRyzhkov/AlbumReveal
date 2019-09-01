package com.daniilryzhkov.albumreveal.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.daniilryzhkov.albumreveal.retrofit.ResultModel;

import java.util.List;

/**
 * Interface for DetailActivity
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface DetailView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void performSingleSearch();

    void showProgressBar();

    void hideProgressBar();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showToast(String message);

    void setDetailInfo(ResultModel info);

    void updateList(List<ResultModel> list);

    void closeActivity();
}
