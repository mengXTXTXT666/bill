package com.tcl.easybill.mvp.model;

import com.tcl.easybill.pojo.TotalBill;

public interface MonthDetailModel {
    /**
     * monthly billing details
     */
    void getMonthDetailBills(String id, String year, String month);

    /**
     * test
     */
    void getDayCost(int id,String year,String month/*,String date*/);
    /**
     * delete bill
     */
    void delete(Long id);

    void update(TotalBill bBill);

    void onUnsubscribe();
}
