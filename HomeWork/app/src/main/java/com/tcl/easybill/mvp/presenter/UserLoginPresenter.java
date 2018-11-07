package com.tcl.easybill.mvp.presenter;

public abstract class UserLoginPresenter extends BasePresenter {
    public abstract void login(String username,String password);

    public abstract void signup(String username,String password,String mail);
}
