package com.daniilryzhkov.albumreveal.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.daniilryzhkov.albumreveal.di.App;
import com.daniilryzhkov.albumreveal.retrofit.ITunesApi;
import com.daniilryzhkov.albumreveal.retrofit.ResponseModel;
import com.daniilryzhkov.albumreveal.view.MainView;
import com.daniilryzhkov.albumreveal.retrofit.ResultModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> implements BasePresenter {

    @Inject
    ITunesApi iTunesApi;

    /**
     * onCreate method
     */
    @Override
    public void startToWork() {
        App.getComponent().inject(this);
        getViewState().requestFocus();
    }

    /**
     * Prepare to search method
     *
     * @param term target album title for a search
     */
    public void performSearch(String term) {
        getViewState().showProgressBar();
        getViewState().hideKeyboard();
        getViewState().clearFocus();
        searchAlbum(term);
    }

    /**
     * Simple method to search
     * Used Retrofit2, RxJava2 and CallbackWrapper abstract class(Error handling)
     *
     * @param term target album title for a search
     */
    private void searchAlbum(String term) {
        iTunesApi.getAlbumsResponse(term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CallbackWrapper<ResponseModel>(this) {
                    @Override
                    public void onSuccess(ResponseModel responseModel) {
                        List<ResultModel> results = responseModel.getResults();
                        getViewState().updateList(sortByName(results));
                        getViewState().hideProgressBar();
                    }
                });
    }

    /**
     * Sort the list by alphabetical type
     *
     * @param results target list
     */
    private List<ResultModel> sortByName(List<ResultModel> results) {
        Collections.sort(results, new Comparator<ResultModel>() {
            @Override
            public int compare(ResultModel album1, ResultModel album2) {
                String albumName1 = album1.getCollectionName();
                String albumName2 = album2.getCollectionName();
                return albumName1.compareTo(albumName2);
            }
        });
        return results;
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
    }
}
