package com.tcl.easybill.mvp.model;

public interface UserLoginModel {
    /**
     * user login
     */
    void login(String username, String password);

    /**
     * user register
     */
    void signup(String username, String password, String mail);

    void onUnsubscribe();
}
