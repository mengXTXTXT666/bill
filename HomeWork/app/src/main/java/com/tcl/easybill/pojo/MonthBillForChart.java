package com.tcl.easybill.pojo;

import java.util.List;


public class MonthBillForChart extends base {
    public MonthBillForChart(float totalOut, float totalIn) {
        this.totalOut = totalOut;
        this.totalIn = totalIn;
    }

    public MonthBillForChart() {
       super();
    }

    float totalOut;    //total out come
    float totalIn;    //total income
    List<SortTypeList> outSortlist;    //账单分类统计支出
    List<SortTypeList> inSortlist;    //账单分类统计收入
    private List<MonthDetailAccount.DaylistBean> daylist;

    public List<MonthDetailAccount.DaylistBean> getDaylist() {
        return daylist;
    }

    public void setDaylist(List<MonthDetailAccount.DaylistBean> daylist) {
        this.daylist = daylist;
    }





    public float getTotalOut() {
        return totalOut;
    }


    public void setTotalOut(float totalOut) {
        this.totalOut = totalOut;
    }

    public float getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(float totalIn) {
        this.totalIn = totalIn;
    }

    public List<SortTypeList> getOutSortlist() {
        return outSortlist;
    }

    public void setOutSortlist(List<SortTypeList> outSortlist) {
        this.outSortlist = outSortlist;
    }

    public List<SortTypeList> getInSortlist() {
        return inSortlist;
    }

    public void setInSortlist(List<SortTypeList> inSortlist) {
        this.inSortlist = inSortlist;
    }

    public static class SortTypeList {
        private String back_color;
        private float  money;    //monthly expense under this sort
        private String sortName;  //bill'sort name
        private String sortImg;
        private List<TotalBill> list;  //monthly bill under this sort

        public String getBack_color() {
            return back_color;
        }

        public void setBack_color(String back_color) {
            this.back_color = back_color;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

        public List<TotalBill> getList() {
            return list;
        }

        public void setList(List<TotalBill> list) {
            this.list = list;
        }

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }

        public String getSortImg() {
            return sortImg;
        }

        public void setSortImg(String sortImg) {
            this.sortImg = sortImg;
        }
    }
}
