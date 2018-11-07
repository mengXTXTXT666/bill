package com.tcl.easybill.mvp.model;

public interface MonthChartModel {
    /**
     * monthly billing chart data
     */
    void getMonthChartBills(String id, String year, String month);

    void getDayDetailBills(int id,String year,String month,String day);

    void onUnsubscribe();
}
