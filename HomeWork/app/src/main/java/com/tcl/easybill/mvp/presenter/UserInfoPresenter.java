package com.tcl.easybill.mvp.presenter;

import com.tcl.easybill.pojo.Person;
import com.tcl.easybill.pojo.User;

public abstract  class UserInfoPresenter extends BasePresenter {

    public abstract void update(Person user);
}
