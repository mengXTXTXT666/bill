package com.tcl.easybill.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.tcl.easybill.R;

import static android.view.View.VISIBLE;

public class SearchAll extends BaseActivity{
    @BindView (R.id.search_exit)
    Button cancelBtn;
    @BindView(R.id.search_enter)
    EditText searchEdit;
    @BindView(R.id.search_text)
    TextView search_text;


    @OnClick ({R.id.search_exit, R.id.search_content})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_exit:
                Log.d("------","exit");
                finish();
                break;
            case R.id.search_content:
                break;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.search_all;
    }

    @Override
    protected void initEventAndData() {
        show();

    }


    private void show(){
        AnimationSet aset=new AnimationSet(true);
        AlphaAnimation show=new AlphaAnimation(0,1);
        AlphaAnimation hide=new AlphaAnimation(1,0);
        aset.addAnimation(show);
        aset.addAnimation(hide);
        aset.setDuration(3000);
        aset.setRepeatMode(5);
        search_text.startAnimation(aset);
    }
}
