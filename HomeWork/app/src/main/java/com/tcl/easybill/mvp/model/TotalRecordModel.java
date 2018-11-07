package com.tcl.easybill.mvp.model;

public interface TotalRecordModel {
    /**
     * get total record
     * @param id user id
     */
    void getTotalRecord(String id);

    void onUnsubscribe();
}
