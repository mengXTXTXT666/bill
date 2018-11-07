package com.tcl.easybill.ui.fragment.login;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.tcl.easybill.R;
import com.tcl.easybill.mvp.presenter.UserLoginPresenter;
import com.tcl.easybill.mvp.presenter.impl.UserLoginPresenterImpl;
import com.tcl.easybill.ui.activity.HomeActivity;
import com.tcl.easybill.ui.activity.UesrLoginActivity;

public class LoginFragment extends Fragment{
    @BindView(R.id.username)
    EditText userName;
    @BindView(R.id.password)
    EditText passWord;
    private Context mContext;
    private Unbinder mUnBinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        this.mContext = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    public String getUserName(){ return userName.getText().toString();}
    public String getPassWord(){ return passWord.getText().toString();}

}
