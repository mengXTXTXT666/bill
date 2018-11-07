package com.tcl.easybill.mvp.presenter.impl;

import com.tcl.easybill.mvp.model.impl.BillMdelImpl;
import com.tcl.easybill.mvp.model.BillModel;
import com.tcl.easybill.mvp.presenter.BillPresenter;
import com.tcl.easybill.mvp.views.BillView;
import com.tcl.easybill.pojo.AllSortBill;
import com.tcl.easybill.pojo.TotalBill;
import com.tcl.easybill.pojo.base;

public class BillPresenterImpl extends BillPresenter implements BillMdelImpl.BillOnListener {
    private BillModel model;
    private BillView view;

    public BillPresenterImpl(BillView view) {
        this.model=new BillMdelImpl(this);
        this.view = view;
    }

    @Override
    public void onSuccess(base bean) {
        view.loadDataSuccess(bean);
    }

    @Override
    public void onSuccess(AllSortBill bean) {
        view.loadDataSuccess(bean);
    }

    @Override
    public void onFailure(Throwable e) {
        view.loadDataError(e);
    }

    @Override
    public void getNote() {
        model.getNote();
    }

    @Override
    public void add(TotalBill bBill) {
        model.add(bBill);
    }

    @Override
    public void update(TotalBill bBill) {
        model.update(bBill);
    }

    @Override
    public void delete(Long id) {
        model.delete(id);
    }
}
