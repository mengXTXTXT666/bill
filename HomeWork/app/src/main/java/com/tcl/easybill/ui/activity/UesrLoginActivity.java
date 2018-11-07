package com.tcl.easybill.ui.activity;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

import com.tcl.easybill.R;
import com.tcl.easybill.Utils.LockViewUtil;
import com.tcl.easybill.Utils.ProgressUtils;
import com.tcl.easybill.Utils.SnackbarUtils;
import com.tcl.easybill.Utils.ToastUtils;
import com.tcl.easybill.mvp.presenter.UserLoginPresenter;
import com.tcl.easybill.mvp.presenter.impl.UserLoginPresenterImpl;
import com.tcl.easybill.mvp.views.UserLoginView;
import com.tcl.easybill.pojo.Person;
import com.tcl.easybill.ui.fragment.login.LoginFragment;
import com.tcl.easybill.ui.fragment.login.RegisterFragment;

public class UesrLoginActivity extends BaseActivity implements UserLoginView{
    @BindView(R.id.button_left)
    Button leftBtn;
    @BindView(R.id.button_right)
    Button rightBtn;
    @BindView(R.id.login_button)
    Button loginButton;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private UserLoginPresenter userLoginPresenter;
    private boolean isLogin = true;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        openLoginFragment();
        userLoginPresenter = new UserLoginPresenterImpl(this);
        if(LockViewUtil.getIslogin(mContext)){
            ProgressUtils.show(this, "正在登录...");
            userLoginPresenter.login(LockViewUtil.getUser(mContext),LockViewUtil.getPassword(mContext));
        }
    }
    /**
     * onclick listener
     *
     * @param view
     */
    @OnClick({R.id.button_left, R.id.button_right,R.id.login_button})
    protected void onClick(View view) {
        switch (view.getId()) {
//            case R.id.button_left:
//                if (!isLogin) {
//                    openLoginFragment();
//                    leftBtn.setText("登陆");
//                    rightBtn.setText("注册");
//                    loginButton.setText("登陆");
//                    isLogin =!isLogin;
//                }
//                break;
            case R.id.button_right:
                if (isLogin) {
                    openRigsterFragment();
                    animationHide();
                    leftBtn.setText("注册");
                    rightBtn.setText("登录");
                    loginButton.setText("注册");
                    animationShow();
                    isLogin =!isLogin;
                }else {
                    openLoginFragment();
                    animationHide();
                    leftBtn.setText("登录");
                    rightBtn.setText("注册");
                    loginButton.setText("登录");
                    animationShow();
                    isLogin =!isLogin;
                }
                break;
            case R.id.login_button:
                if(isLogin){
                    login();
                }else {
                    register();
                }
                break;
        }
    }

    public void login(){
        String username = loginFragment.getUserName();
        String password = loginFragment.getPassWord();
        if (username.length() == 0 || password.length() == 0) {
            SnackbarUtils.show(mContext, "用户名或密码不能为空");
            //return;
        }
        LockViewUtil.saveUser(mContext, username);
        LockViewUtil.savePassword(mContext, password);
        ProgressUtils.show(this, "正在登录...");
        userLoginPresenter.login(username,password);
       // userLoginPresenter.login("1", "1");

    }
    public void register(){
        String username = registerFragment.getUserName();
        String password = registerFragment.getPassWord();
        String Email = registerFragment.getEmail();
        if (username.length() == 0 || password.length() == 0) {
            SnackbarUtils.show(mContext, "用户名或密码不能为空");
            return;
        }
        userLoginPresenter.signup(username,password,Email);
    }

    @Override
    public void loadDataSuccess(Person tData) {
        //ProgressUtils.dismiss();
        if (isLogin) {
            Log.i(TAG, "is" + LockViewUtil.getIslock(mContext));
            if(LockViewUtil.getIslock(mContext)){
                startActivity(new Intent(getApplicationContext(), UnlockUI.class));
                finish();
            }else {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
            setResult(RESULT_OK, new Intent());
            finish();
            LockViewUtil.setIslogin(mContext, true);
        }else {
            ToastUtils.show(mContext, "注册成功");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProgressUtils.dismiss();
    }


    @Override
    public void loadDataError(Throwable throwable) {
        ProgressUtils.dismiss();
        SnackbarUtils.show(mContext, throwable.getMessage());
    }

    /**
     *Switch to login interface
     */
    private void openLoginFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        loginFragment = new LoginFragment();
        transaction.replace(R.id.fragment_change, loginFragment);
        transaction.commit();
    }

    /**
     * switch to register interface
     */
    private void openRigsterFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        registerFragment = new RegisterFragment();
        transaction.replace(R.id.fragment_change, registerFragment);
        transaction.commit();
    }


    public void animationShow(){
        AnimationSet aset=new AnimationSet(true);
        AlphaAnimation aa=new AlphaAnimation(0,1);
        aa.setDuration(2000);
        aset.addAnimation(aa);
        leftBtn.startAnimation(aset);
        rightBtn.startAnimation(aset);
        loginButton.startAnimation(aset);
    }

    public void animationHide(){
        AnimationSet aset=new AnimationSet(true);
        AlphaAnimation aa=new AlphaAnimation(1,0);
        aa.setDuration(2000);
        aset.addAnimation(aa);
        leftBtn.startAnimation(aset);
        rightBtn.startAnimation(aset);
        loginButton.startAnimation(aset);
    }
}
