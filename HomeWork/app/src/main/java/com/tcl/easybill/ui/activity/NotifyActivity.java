package com.tcl.easybill.ui.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.tcl.easybill.MyBroadcast;
import com.tcl.easybill.R;
import com.tcl.easybill.Utils.LockViewUtil;
import com.tcl.easybill.ui.adapter.NotifyAdpter;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.view.View.INVISIBLE;

public class NotifyActivity extends BaseActivity{
    @BindView(R.id.notify_view)
    RecyclerView notifyView;
    @BindView(R.id.notify_layout)
    ConstraintLayout notifyLayout;
    @BindView(R.id.notify_background)
    LinearLayout notifyBackground;

    private ArrayList<String> mDatas;
    private NotifyAdpter nAdapter;
    private Boolean isFirst = true;
    private String show;
    @Override
    protected int getLayout() {
        return R.layout.notify;
    }

    /*read the alarm that set before*/
    @Override
    protected void initEventAndData() {
        if(LockViewUtil.getIsSet(mContext) && !(LockViewUtil.getCalender(mContext).equals(""))){
            String data = LockViewUtil.getCalender(mContext);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>() {

            }.getType();
            mDatas = gson.fromJson(data, listType);
            notifyView.setLayoutManager(new LinearLayoutManager(this));
            notifyView.setAdapter(nAdapter = new NotifyAdpter(mContext, mDatas));
            notifyBackground.setVisibility(INVISIBLE);
            nAdapter.setOnNotifyClickListener(new NotifyAdpter.OnNotifyClickListener() {
                @Override
                public void OnClick(int index) {
                    delete(index);
                }
            });
        }
    }

    @OnClick({R.id.notify_add, R.id.back_notify})
    protected void onClick(View v){
        switch (v.getId()){
            case R.id.notify_add:
                clockSet();
                break;
            case R.id.back_notify:
                finish();
                break;
        }
    }

    /**
     * call a dialog to set the notify msg
     */
    private void clockSet(){
        final EditText editText = new EditText(mContext);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("定时提醒");
        builder.setMessage("请输入内容");
        builder.setView(editText);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = editText.getText().toString();
                Log.v(TAG + ":clockSet", input);
                clockSetting(input);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "取消", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    /**
     * alarm setting
     */
    private void clockSetting(final String string){
        Log.v(TAG + ":clockSetting", string);
        Calendar currentTime = Calendar.getInstance();
        new TimePickerDialog(mContext, 0, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /*set alarm to send a Broadcast*/
                Intent intent = new Intent();
                intent.setAction("com.tcl.easybill.RECEVIER");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.setComponent(new ComponentName("com.tcl.easybill",
                            "com.tcl.easybill.MyBroadcast"));
                }
                intent.putExtra("msg", string);
                PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, intent,
                        0);
                Calendar c = Calendar.getInstance();
                //set alarm time
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                //get the system's AlarmManager
                AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
                /*不建议使用RTC_WAKEUP*/
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                int Hour = c.get(Calendar.HOUR_OF_DAY);
                int Minu = c.get(Calendar.MINUTE);
                if(Minu >= 10) {
                    show = String.valueOf(Hour) + ":" + String.valueOf(Minu);
                }else {
                    show = String.valueOf(Hour) + ":0" + String.valueOf(Minu);
                }

                pi.cancel();
                /*call initRecyclerView() if it's the first time to set alarm*/
                if(!LockViewUtil.getIsSet(mContext)) {
                    initRecyclerView(show);
                    Log.i("----","isfirst");
                    notifyBackground.setVisibility(INVISIBLE);
                    LockViewUtil.setIsSet(mContext,true);
                }else {
                    nAdapter.addData(mDatas.size(), show);
                }
            }
        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false).show();
    }

    /**
     *set the first alarm
     */
    private void initRecyclerView(String time){
        mDatas = new ArrayList<String>();
        mDatas.add(time);
        notifyView.setLayoutManager(new LinearLayoutManager(this));
        notifyView.setAdapter(nAdapter = new NotifyAdpter(mContext, mDatas));
        nAdapter.setOnNotifyClickListener(new NotifyAdpter.OnNotifyClickListener() {
            @Override
            public void OnClick(int index) {
                delete(index);
            }
        });
    }

    /**
     * delete the alarm
     */
    private void  delete(final int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("定时提醒" + (index + 1));
        builder.setMessage("是否删除该提醒");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                nAdapter.removeData(index);
                if(mDatas.size() == 0){
                    LockViewUtil.setIsSet(mContext,false);
                }
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "取消", Toast.LENGTH_LONG).show();
            }
        });
        builder.create().show();
    }

    /**
     * save the data when this activity is finished
     */
    @Override
    protected void onDestroy() {
        LockViewUtil.saveCalender(mContext, mDatas);
        super.onDestroy();
    }
}
