package com.tcl.easybill.pojo;

import java.util.List;

public class AllSortBill extends base {
    private List<SortBill> outSortList;
    private List<SortBill> inSortList;
    private List<BPay> payinfo;

    public List<BPay> getPayinfo() {
        return payinfo;
    }

    public void setPayinfo(List<BPay> payinfo) {
        this.payinfo = payinfo;
    }

    public List<SortBill> getOutSortList() {
        return outSortList;
    }

    public void setOutSortList(List<SortBill> outSortList) {
        this.outSortList = outSortList;
    }

    public List<SortBill> getInSortList() {
        return inSortList;
    }

    public void setInSortList(List<SortBill> inSortList) {
        this.inSortList = inSortList;
    }
}
