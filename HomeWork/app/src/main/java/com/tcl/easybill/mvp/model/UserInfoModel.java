package com.tcl.easybill.mvp.model;


import com.tcl.easybill.pojo.Person;
import com.tcl.easybill.pojo.User;

public interface UserInfoModel {

    void update(Person user);

    void onUnsubscribe();
}
