package com.tcl.easybill.ui.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tcl.easybill.R;

import static android.app.Notification.BADGE_ICON_SMALL;
import static com.tcl.easybill.base.Constants.BUDGET;
import static com.tcl.easybill.base.Constants.HOME;

public class NotificationActivity   {
    private NotificationManager mNotificationManager;
    private Context context;
    private int activity;
    private  TaskStackBuilder stackBuilder;

    private String groupId = "groupId";
    private CharSequence groupName = "Group1";

    private String groupId2 = "groupId2";
    private CharSequence groupName2 = "Group2";
    private String chatChannelId2 = "chatChannelId2";
    private String adChannelId2 = "adChannelId2";

    private String chatChannelId = "chatChannelId";
    private String chatChannelName = "聊天通知";
    private String chatChannelDesc = "这是一个聊天通知，建议您置于开启状态，这样才不会漏掉女朋友的消息哦";
    private int chatChannelImportance = NotificationManager.IMPORTANCE_MAX;

    private String adChannelId = "adChannelId";
    private String adChannelName = "广告通知";
    private String adChannelDesc = "这是一个广告通知，可以关闭的，但是如果您希望我们做出更好的软件服务于你，请打开广告支持一下吧";
    private int adChannelImportance = NotificationManager.IMPORTANCE_LOW;

    public NotificationActivity(NotificationManager mNotificationManager,Context context,int activity) {
        this.mNotificationManager = mNotificationManager;
        this.context = context;
        this.activity=activity;
        createGroup();
    }


    public void notification( ) {
        createNotificationChannel(chatChannelId, chatChannelName, chatChannelImportance, chatChannelDesc, groupId2);

        Notification.Builder builder = new Notification.Builder(context, chatChannelId);
        builder.setSmallIcon(R.mipmap.yusuan)
                .setContentTitle("上书")
                .setContentText("本月支出已超出预算额度，请吾皇瞧瞧国库")
                .setBadgeIconType(BADGE_ICON_SMALL)
                .setNumber(1)
                .setAutoCancel(true);
        if (activity==HOME){
            /*Intent resultIntent = new Intent(context, BudgetActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(BudgetActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);*/
        }else if(activity==BUDGET){
            Intent resultIntent = new Intent(context, HomeActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(HomeActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(resultPendingIntent);
        }



        mNotificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
    public void createGroup() {
        mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, groupName));
        mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId2, groupName2));

        createNotificationChannel(chatChannelId2, chatChannelName, chatChannelImportance, chatChannelDesc, groupId);
        createNotificationChannel(adChannelId2, adChannelName, adChannelImportance, adChannelDesc, groupId);
    }
    public void createNotificationChannel(String id, String name, int importance, String desc, String groupId) {
        if (mNotificationManager.getNotificationChannel(id) != null) return;

        NotificationChannel notificationChannel = new NotificationChannel(id, name, importance);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);

        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationChannel.setShowBadge(true);
        notificationChannel.setBypassDnd(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400});
        notificationChannel.setDescription(desc);
        notificationChannel.setGroup(groupId);
//        notificationChannel.setSound();

        mNotificationManager.createNotificationChannel(notificationChannel);
    }
}
