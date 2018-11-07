package com.tcl.easybill.ui.activity;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import com.tcl.easybill.R;
import com.tcl.easybill.Utils.SnackbarUtils;
import com.tcl.easybill.Utils.ToastUtils;
import com.tcl.easybill.base.SyncEvent;
import com.tcl.easybill.mvp.presenter.UserInfoPresenter;
import com.tcl.easybill.mvp.presenter.impl.UserInfoPresenterImp;
import com.tcl.easybill.mvp.views.MonthDetailView;
import com.tcl.easybill.mvp.views.UserInfoView;
import com.tcl.easybill.pojo.MonthDetailAccount;
import com.tcl.easybill.pojo.Person;
import com.tcl.easybill.pojo.base;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
import static com.tcl.easybill.base.Constants.BUDGET;
import static com.tcl.easybill.base.Constants.NOTIFY;

public class BudgetActivity extends BaseActivity implements UserInfoView{
    @BindView(R.id.all_content)
    TextView budgetText;
    @BindView(R.id.progressBar)
    ProgressBar surplusProgressBar;
    @BindView(R.id.totaloutcome)
    TextView totaloutcome;
    @BindView(R.id.shengyu_content)
    TextView last;
    private String input;
    private UserInfoPresenter presenter;
    /*private Float sum;*/
    @Override
    protected  int getLayout(){
        return R.layout.budget;
    }

    protected  void initEventAndData(){
        setBudget();
        presenter = new UserInfoPresenterImp(this);

    }

    /**
     * set the surplus
     */
    @OnClick (R.id.edit_surplus)
    public void editSurplus(){
        final EditText enterSurplus = new EditText(this);
        enterSurplus.setHint(R.string.surplus_set);
        enterSurplus.setInputType(TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("每月预算");
        builder.setView(enterSurplus);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*get setting budget*/
                input = enterSurplus.getText().toString();
                budgetText.setText(input);
                surplusProgressBar.setMax(Integer.valueOf(input));
                surplusProgressBar.setProgress((Integer.valueOf(input))/2);
                currentUser.setBudget(input);
                presenter.update(currentUser);

                Toast.makeText(mContext, "设置成功", Toast.LENGTH_LONG).show();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "取消", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }
    public void setBudget(){
        if (currentUser.getBudget()!=null && !currentUser.getBudget().isEmpty()){
            Intent intent = getIntent();
            String budget = currentUser.getBudget();
            String outcome = intent.getStringExtra("outcome");
            budgetText.setText(budget);
            totaloutcome.setText(outcome);
            Float sum=0f;
            try {
                 sum = Float.valueOf(budget) - Float.valueOf(outcome);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (sum <=0 && NOTIFY<2){
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationActivity activity = new NotificationActivity(mNotificationManager,getApplicationContext(),BUDGET);
                activity.notification();
                NOTIFY=2;
            }
            last.setText(sum.toString());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
       /* EventBus.getDefault().unregister(this);*/
    }

    @OnClick (R.id.budget_back)
    public void backToParent(){
        finish();
    }


    @Override
    public void loadDataSuccess(Person tData) {
        setBudget();
    }

    @Override
    public void loadDataError(Throwable throwable) {
       /* EventBus.getDefault().unregister(this);*/
    }
}
