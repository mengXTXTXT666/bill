package com.tcl.easybill.ui.fragment.login;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcl.easybill.R;

public class RegisterFragment extends Fragment{
    private EditText userName, passWord, Email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        return inflater.inflate(R.layout.register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName = getActivity().findViewById(R.id.enter_account);
        passWord = getActivity().findViewById(R.id.enter_cyper);
        Email = getActivity().findViewById(R.id.enter_verify);
    }

    public String getUserName(){ return userName.getText().toString();}
    public String getPassWord(){ return passWord.getText().toString();}
    public String getEmail(){
        return Email.getText().toString();
    }
}
