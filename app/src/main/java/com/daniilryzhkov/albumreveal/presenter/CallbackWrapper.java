package com.daniilryzhkov.albumreveal.presenter;

import com.daniilryzhkov.albumreveal.retrofit.ResponseModel;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * This is wrapper for error handling in a single place
 *
 * @param <T> ResponseModel
 */
public abstract class CallbackWrapper<T extends ResponseModel> extends DisposableObserver<T> {

    private WeakReference<BasePresenter> weakReference;

    public CallbackWrapper(BasePresenter view) {
        this.weakReference = new WeakReference<>(view);
    }

    protected abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        BasePresenter view = weakReference.get();
        if (e instanceof HttpException) {
            int code = ((HttpException) e).code();
            view.onError("Http error " + code);
        } else if (e instanceof SocketTimeoutException) {
            view.onError("TimeOut error");
        } else if (e instanceof IOException) {
            view.onError("Network error");
        } else {
            view.onError(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }
}
