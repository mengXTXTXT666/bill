package com.tcl.easybill.mvp.presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter {


    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * RXjava cancel register
     */
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    /**
     * RXjava register
     * @param disposable
     */
    public void register(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

}
