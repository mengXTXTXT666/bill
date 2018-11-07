package com.tcl.easybill.mvp.presenter;


import com.tcl.easybill.pojo.TotalBill;

public abstract  class BillPresenter extends BasePresenter {

    /**
     * get total data
     */
    public abstract void getNote();

    /**
     * add bill
     */
    public abstract void add(TotalBill totalBill);

    /**
     * revise bill
     */
    public abstract void update(TotalBill totalBill);


    /**
     * delete bill
     */
    public abstract void delete(Long id);
}
