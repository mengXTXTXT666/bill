package com.tcl.easybill.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LockViewUtil {
    //手势密码
    private static final String SP_NAME = "LOCKVIEW";
    private static final String SP_KEY = "PASSWORD";
    //是否上锁
    private static final String ISLOCK = "status";
    //是否第一次使用，用于引导页
    private static final String ISFIRST = "times";
    //时间记录，用于定时提醒
    private static final String TIME_H = "hour";
    private static final String IS_SET = "set";
    //记录是否更改了头像
    private static final String ISCHANGE = "head";
    private static final String IMAGE = "image";
    //是否登录
    private static final String ISLOGIN = "login";
    private static final String USER = "null";
    private static final String PASSWORD = "null";
    public static void savePwd(Context mContext , List<Integer> password){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(SP_KEY, listToString(password)).commit();
    }

    public static String getPwd(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(SP_KEY, "");
    }

    public static void clearPwd(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().remove(SP_KEY).commit();
    }

    public static String listToString(List<Integer> lists){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < lists.size(); i++){
            sb.append(lists.get(i));
        }
        return sb.toString();
    }

    public static List<Integer> stringToList(String string){
        List<Integer> lists = new ArrayList<>();
        for(int i = 0; i < string.length(); i++){
            lists.add(Integer.parseInt(string.charAt(i) + ""));
        }
        return lists;
    }

    public static void setIslock(Context mContext, Boolean islock){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(ISLOCK, islock).commit();
    }

    public static boolean getIslock(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(ISLOCK, false);
    }

    public static boolean getIsfirst(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(ISFIRST, true);
    }

    public static void setIsfirst(Context mContext, Boolean isfirst){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(ISFIRST, isfirst).commit();
    }

    public static boolean getIsSet(Context mContext) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(IS_SET, false);
    }

    public static void setIsSet (Context mContext, Boolean isSet){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(IS_SET, isSet).apply();
    }

    public static void saveCalender(Context mContext, ArrayList<String> list){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = gson.toJson(list);
        sp.edit().putString(TIME_H, data).apply();
    }

    public static String getCalender(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
       return  sp.getString(TIME_H, "");
    }

    public static void setIschange(Context mContext, Boolean ischange){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(ISCHANGE, ischange).apply();
    }

    public static boolean getIschange(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(ISCHANGE, false);
    }

    public static void saveImage(Context mContext, String image){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);;
        sp.edit().putString(IMAGE, image).apply();
    }

    public static String getImage(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return  sp.getString(IMAGE, "");
    }

    public static void clearImage(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().remove(IMAGE).apply();
    }

    /*save user and password */
    public static void setIslogin(Context mContext, Boolean islogin){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(ISLOGIN, islogin).apply();
    }

    public static Boolean getIslogin(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(ISLOGIN, false);
    }

    public static void saveUser(Context mContext, String user){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().remove(USER).apply();
        sp.edit().putString(USER, user).apply();
    }

    public static String getUser(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return  sp.getString(USER, "");
    }

    public static void savePassword(Context mContext, String password){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().remove(PASSWORD).apply();
        sp.edit().putString(PASSWORD, password).apply();
    }

    public static String getPassword(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return  sp.getString(PASSWORD, "");
    }
}

