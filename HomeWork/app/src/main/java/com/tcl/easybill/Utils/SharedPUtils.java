package com.tcl.easybill.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import com.tcl.easybill.pojo.AllSortBill;


public class SharedPUtils {

    public final static String USER_INFOR = "userInfo";
    public final static String USER_SETTING = "userSetting";


    /**
     * 获取当前用户账单分类信息
     */
    public static AllSortBill getUserNoteBean(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFOR, Context.MODE_PRIVATE);
        if (sp != null) {
            String jsonStr = sp.getString("noteBean", null);
            if (jsonStr != null) {
                Gson gson = new Gson();
                return gson.fromJson(jsonStr, AllSortBill.class);
            }
        }
        return null;
    }

    /**
     * 设置当前用户账单分类信息
     */
    public static void setUserNoteBean(Context context, String jsonStr) {
        SharedPreferences sp = context.getSharedPreferences(USER_INFOR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("noteBean", jsonStr);
        editor.commit();
    }

    /**
     * 设置当前用户账单分类信息
     */
    public static void setUserNoteBean(Context context, AllSortBill noteBean) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(noteBean);
        SharedPreferences sp = context.getSharedPreferences(USER_INFOR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("noteBean", jsonStr);
        editor.commit();
    }



    /**
     * 判断是否第一次进入APP
     * @param context
     * @return
     */
    public static boolean isFirstStart(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        boolean isFirst= sp.getBoolean("first", true);
        //第一次则修改记录
        if(isFirst)
            sp.edit().putBoolean("first", false).commit();
        return isFirst;
    }
}
