package com.tcl.easybill.pojo;

import java.util.List;

public class MonthDetailAccount extends base {
    private String t_income;
    private String t_outcome;
    private List<DaylistBean> daylist;

    public String getT_income() {
        return t_income;
    }

    public void setT_income(String t_income) {
        this.t_income = t_income;
    }

    public String getT_outcome() {
        return t_outcome;
    }

    public void setT_outcome(String t_outcome) {
        this.t_outcome = t_outcome;
    }

    public List<DaylistBean> getDaylist() {
        return daylist;
    }

    public void setDaylist(List<DaylistBean> daylist) {
        this.daylist = daylist;
    }

    public static class DaylistBean {

        private String time;
        private String money;
        private List<TotalBill> list;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public List<TotalBill> getList() {
            return list;
        }

        public void setList(List<TotalBill> list) {
            this.list = list;
        }
    }
}
