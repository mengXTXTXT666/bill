package com.tcl.easybill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.tcl.easybill.ui.widget.NotificationTool;

public class MyBroadcast extends BroadcastReceiver {
    /**
     * use to send a notification
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String extras = intent.getStringExtra("msg");
        Log.i("111", extras);
        NotificationTool notificationTool = new NotificationTool(context);
        notificationTool.setTitle("定时提醒");
        notificationTool.setMsg(extras);
        notificationTool.sendNotify();

    }
}
