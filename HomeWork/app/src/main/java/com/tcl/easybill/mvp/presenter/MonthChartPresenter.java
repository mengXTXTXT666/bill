package com.tcl.easybill.mvp.presenter;

public abstract class MonthChartPresenter extends BasePresenter{

    public abstract void getMonthChartBills(String id,String year,String month);

    public abstract void getDayDetailBills(int id,String year,String month,String day);
}
