package com.tcl.easybill.mvp.presenter;

import com.tcl.easybill.pojo.TotalBill;

public abstract class MonthDetailPresenter {

    public abstract void getMonthDetailBills(String id,String year,String month);
    public abstract void getDayCost(int id, String year, String month/*,String date*/);
    public abstract void deleteBill(Long id);

    public abstract void updateBill(TotalBill bBill);
}
