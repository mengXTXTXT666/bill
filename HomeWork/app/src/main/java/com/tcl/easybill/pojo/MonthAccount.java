package com.tcl.easybill.pojo;

import java.util.List;

public class MonthAccount extends base {
    float totalOut;    //total outcome
    float totalIn;    //total income
    List<PayTypeListBean> list;    //bill's sort statistics

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

    public List<PayTypeListBean> getList() {
        return list;
    }

    public void setList(List<PayTypeListBean> list) {
        this.list = list;
    }

    public static class PayTypeListBean {
        String payName;
        String payImg;
        float outcome;
        float income;
        List<TotalBill> Bills;

        public String getPayName() {
            return payName;
        }

        public void setPayName(String payName) {
            this.payName = payName;
        }

        public String getPayImg() {
            return payImg;
        }

        public void setPayImg(String payImg) {
            this.payImg = payImg;
        }

        public float getOutcome() {
            return outcome;
        }

        public void setOutcome(float outcome) {
            this.outcome = outcome;
        }

        public float getIncome() {
            return income;
        }

        public void setIncome(float income) {
            this.income = income;
        }

        public List<TotalBill> getBills() {
            return Bills;
        }

        public void setBills(List<TotalBill> bills) {
            Bills = bills;
        }
    }
}
