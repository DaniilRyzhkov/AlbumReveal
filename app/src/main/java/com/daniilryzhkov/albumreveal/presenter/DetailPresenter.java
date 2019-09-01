package com.daniilryzhkov.albumreveal.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.daniilryzhkov.albumreveal.di.App;
import com.daniilryzhkov.albumreveal.retrofit.ITunesApi;
import com.daniilryzhkov.albumreveal.view.DetailView;
import com.daniilryzhkov.albumreveal.retrofit.ResponseModel;
import com.daniilryzhkov.albumreveal.retrofit.ResultModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter for DetailActivity
 */
@InjectViewState
public class DetailPresenter extends MvpPresenter<DetailView> implements BasePresenter {

    @Inject
    ITunesApi iTunesApi;

    /**
     * onCreate method
     */
    public void startToWork() {
        App.getComponent().inject(this);
        getViewState().performSingleSearch();
    }

    /**
     * Prepare to search method
     *
     * @param collectionId target id for a search
     */
    public void performSearch(int collectionId) {
        getViewState().showProgressBar();
        searchSong(collectionId);
    }

    /**
     * Simple method to search
     * Used Retrofit2, RxJava2 and CallbackWrapper abstract class(Error handling)
     *
     * @param collection_id target id for a search
     */
    private void searchSong(int collection_id) {
        iTunesApi.getTracksResponse(collection_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CallbackWrapper<ResponseModel>(this) {
                    @Override
                    public void onSuccess(ResponseModel responseModel) {
                        List<ResultModel> results = responseModel.getResults();
                        ResultModel info = results.get(0); // Get collection's info
                        results.remove(0); // Remove collection's info, retaining track list only
                        getViewState().updateList(results);
                        getViewState().setDetailInfo(info);
                        getViewState().hideProgressBar();
                    }
                });
    }

    /**
     * BasePresenter override, from CallbackWrapper calls
     *
     * @param message error message
     */
    @Override
    public void onError(String message) {
        getViewState().showToast(message);
        getViewState().hideProgressBar();
        getViewState().closeActivity();
    }
}
