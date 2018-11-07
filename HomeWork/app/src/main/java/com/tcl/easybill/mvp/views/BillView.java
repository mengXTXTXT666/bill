package com.tcl.easybill.mvp.views;

import com.tcl.easybill.pojo.AllSortBill;
import com.tcl.easybill.pojo.base;

public interface BillView extends BaseView<base>{

    void loadDataSuccess(AllSortBill tData);
}
