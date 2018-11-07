package com.tcl.easybill.mvp.presenter.impl;

import com.tcl.easybill.mvp.model.MonthDetailModel;
import com.tcl.easybill.mvp.model.impl.MonthDetailModelImpl;
import com.tcl.easybill.mvp.presenter.MonthBillPresenter;
import com.tcl.easybill.mvp.presenter.MonthDetailPresenter;
import com.tcl.easybill.mvp.views.MonthDetailView;
import com.tcl.easybill.pojo.MonthDetailAccount;
import com.tcl.easybill.pojo.TotalBill;
import com.tcl.easybill.pojo.base;

public class MonthDetailPresenterImpl extends MonthDetailPresenter implements MonthDetailModelImpl.MonthDetailOnListener {
    private MonthDetailModel monthDetailModel;
    private MonthDetailView monthDetailView;

    public MonthDetailPresenterImpl(MonthDetailView monthDetailView) {
        this.monthDetailModel=new MonthDetailModelImpl(this);
        this.monthDetailView = monthDetailView;
    }
    @Override
    public void getMonthDetailBills(String id, String year, String month) {
        monthDetailModel.getMonthDetailBills(id,year,month);
    }

    @Override
    public void getDayCost(int id, String year, String month/*,String date*/) {
        monthDetailModel.getDayCost(id,year,month/*,date*/);
    }

    @Override
    public void deleteBill(Long id) {
        monthDetailModel.delete(id);
    }

    @Override
    public void updateBill(TotalBill bBill) {
        monthDetailModel.update(bBill);
    }

    @Override
    public void onSuccess(MonthDetailAccount bean) {
        monthDetailView.loadDataSuccess(bean);
    }

    @Override
    public void onSuccess(base bean) {
        monthDetailView.loadDataSuccess(bean);
    }

    @Override
    public void onFailure(Throwable e) {
        monthDetailView.loadDataError(e);
    }
}
