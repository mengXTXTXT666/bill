package com.tcl.easybill.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import cn.bmob.v3.Bmob;

public class MyApplication extends MultiDexApplication {

    public static MyApplication application;
    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();

        //base data to connect BOMB
        Bmob.initialize(this, "67c270494e715778bc806baefbb410c3");
    }

    /**
     * get context
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
