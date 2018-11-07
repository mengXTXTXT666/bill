package com.tcl.easybill.mvp.presenter.impl;
import com.tcl.easybill.mvp.model.UserInfoModel;
import com.tcl.easybill.mvp.model.impl.UserInfoModelImp;
import com.tcl.easybill.mvp.presenter.UserInfoPresenter;
import com.tcl.easybill.mvp.views.UserInfoView;
import com.tcl.easybill.pojo.Person;
import com.tcl.easybill.pojo.User;

public class UserInfoPresenterImp extends UserInfoPresenter implements UserInfoModelImp.UserInfoOnListener {

    private UserInfoModel model;
    private UserInfoView view;

    public UserInfoPresenterImp(UserInfoView view) {
        this.model=new UserInfoModelImp(this);
        this.view = view;
    }

    @Override
    public void onSuccess(Person user) {
        view.loadDataSuccess(user);
    }

    @Override
    public void onFailure(Throwable e) {
        view.loadDataError(e);
    }

    @Override
    public void update(Person user) {
        model.update(user);
    }
}
