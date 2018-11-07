package com.tcl.easybill.mvp.presenter.impl;

import com.tcl.easybill.mvp.model.TotalRecordModel;
import com.tcl.easybill.mvp.model.impl.TotalRecordModelImpl;
import com.tcl.easybill.mvp.presenter.TotalRecordPresenter;
import com.tcl.easybill.mvp.views.TotalRecordView;
import com.tcl.easybill.pojo.DataSum;
import com.tcl.easybill.pojo.TotalBill;

public class TotalRecordPresenterImpl extends TotalRecordPresenter implements TotalRecordModelImpl.TotalRecordOnListener {
    private TotalRecordModel model;
    private TotalRecordView view;

    public TotalRecordPresenterImpl(TotalRecordView view) {
       this.model = new TotalRecordModelImpl(this);
       this.view = view;
    }

    @Override
    public void getTotalRecord(String id) {
        model.getTotalRecord(id);
    }

    @Override
    public void onSuccess(DataSum bean) {
        view.loadDataSuccess(bean);
    }

    @Override
    public void onFailure(Throwable e) {
        view.loadDataError(e);
    }
}
