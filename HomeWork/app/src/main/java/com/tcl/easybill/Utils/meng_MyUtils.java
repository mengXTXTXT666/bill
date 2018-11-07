package com.tcl.easybill.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class meng_MyUtils {
    /**
     * 根据日期字符串判断当月第几周
     * @param str
     * @return
     * @throws Exception
     */
    public static int getWeek(String str) throws Exception{
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date =sdf.parse(str);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //第几天，从周日开始
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return week;
    }

    public String getMonth(String month){
        if (month.substring(5,6).equals("0")){
            month = month.substring(6,7);
        }else{
            month = month.substring(5,7);
        }
        return month;
    }
    public String getYear(String year){
        return year.substring(0,4);
    }
    public String getDay(String day){
        if (day.substring(7,8).equals("0")){
            day = day.substring(8,9);
        }else {
            day = day.substring(7,9);
        }
        return day;
    }
}
