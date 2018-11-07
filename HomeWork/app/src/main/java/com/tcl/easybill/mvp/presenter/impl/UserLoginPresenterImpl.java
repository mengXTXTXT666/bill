package com.tcl.easybill.mvp.presenter.impl;

import com.tcl.easybill.mvp.model.UserLoginModel;
import com.tcl.easybill.mvp.model.impl.UserLoginModelImpl;
import com.tcl.easybill.mvp.presenter.UserLoginPresenter;
import com.tcl.easybill.mvp.views.UserLoginView;
import com.tcl.easybill.pojo.Person;
import com.tcl.easybill.pojo.User;

public class UserLoginPresenterImpl extends UserLoginPresenter implements UserLoginModelImpl.UserLoginOnListener {
    private UserLoginView view;
    private UserLoginModel model;

    public UserLoginPresenterImpl (UserLoginView view){
        this.model = new UserLoginModelImpl(this);
        this.view = view;
    }

    @Override
    public void login(String username, String password) {
        model.login(username,password);
    }

    @Override
    public void signup(String username, String password, String mail) {
        model.signup(username,password,mail);
    }

    @Override
    public void onSuccess(Person user) {
        view.loadDataSuccess(user);
    }

    @Override
    public void onFailure(Throwable e) {
        view.loadDataError(e);
    }
}
