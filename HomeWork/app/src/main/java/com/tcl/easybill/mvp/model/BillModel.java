package com.tcl.easybill.mvp.model;


import com.tcl.easybill.pojo.TotalBill;

public interface BillModel {

    void getNote();
    /**
     * add bill
     */
    void add(TotalBill totalBill);
    /**
     * revise bill
     */
    void update(TotalBill totalBill);
    /**
     * delete bill
     */
    void delete(Long id);

    void onUnsubscribe();
}
