package com.tcl.easybill.mvp.presenter.impl;

import com.tcl.easybill.mvp.model.MonthBillModel;
import com.tcl.easybill.mvp.model.impl.MonthBillModelImpl;
import com.tcl.easybill.mvp.presenter.MonthBillPresenter;
import com.tcl.easybill.mvp.views.MonthBillView;
import com.tcl.easybill.pojo.MonthAccount;

public class MonthBillPresenterImpl extends MonthBillPresenter implements MonthBillModelImpl.MonthBillOnListener {

    private MonthBillModel monthBillModel;
    private MonthBillView monthBillView;

    public MonthBillPresenterImpl(MonthBillView view){
        this.monthBillView = view;
        this.monthBillModel = new MonthBillModelImpl(this);
    }
    @Override
    public void getMonthBills(int id, String year, String month) {
        monthBillModel.getMonthBills(id,year,month);
    }

    @Override
    public void onSuccess(MonthAccount bean) {
        monthBillView.loadDataSuccess(bean);
    }

    @Override
    public void onFailure(Throwable e) {
        monthBillView.loadDataError(e);
    }
}
