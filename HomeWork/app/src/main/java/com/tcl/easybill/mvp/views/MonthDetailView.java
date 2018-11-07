package com.tcl.easybill.mvp.views;

import com.tcl.easybill.pojo.MonthDetailAccount;
import com.tcl.easybill.pojo.base;

public interface  MonthDetailView extends BaseView<MonthDetailAccount> {

    /**
     * local monthly bill
     * @param list
     */
    void loadDataSuccess(MonthDetailAccount list);
    /**
     * delete data success
     * @param tData
     */
    void loadDataSuccess(base tData);
}
