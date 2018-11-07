package com.tcl.easybill.mvp.presenter.impl;

import com.tcl.easybill.mvp.model.MonthChartModel;
import com.tcl.easybill.mvp.model.impl.MonthChartModelImpl;
import com.tcl.easybill.mvp.presenter.MonthChartPresenter;
import com.tcl.easybill.mvp.views.MonthChartView;
import com.tcl.easybill.pojo.MonthBillForChart;

public class MonthChartPresenterImpl extends MonthChartPresenter implements MonthChartModelImpl.MonthChartOnListener {
    private MonthChartModel model;
    private MonthChartView view;

    public MonthChartPresenterImpl(MonthChartView view) {
        this.model=new MonthChartModelImpl(this);
        this.view = view;
    }
    @Override
    public void getMonthChartBills(String id, String year, String month) {
        model.getMonthChartBills(id,year,month);
    }

    @Override
    public void getDayDetailBills(int id, String year, String month, String day) {
        model.getDayDetailBills(id,year,month,day);
    }

    @Override
    public void onSuccess(MonthBillForChart bean) {
        view.loadDataSuccess(bean);
    }

    @Override
    public void onFailure(Throwable e) {
        view.loadDataError(e);
    }
}
