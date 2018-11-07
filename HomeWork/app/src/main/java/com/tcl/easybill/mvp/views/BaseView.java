package com.tcl.easybill.mvp.views;

public interface BaseView<T> {
    /**
     * load data success
     * @param tData
     */
    void loadDataSuccess(T tData);

    /**
     * load data error
     * @param throwable
     */
    void loadDataError(Throwable throwable);
}
