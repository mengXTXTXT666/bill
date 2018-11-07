package com.tcl.easybill.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Objects;
import org.greenrobot.greendao.annotation.Generated;

import cn.bmob.v3.BmobObject;

@Entity
public class ShareBill extends BmobObject {
    @Id
    private String id;  //objectID in server
    private float cost;
    private String content;  //remarks in bill
    private String userid;
    private String payName;
    private String payImg;
    private String sortName; //sort of bill
    private String sortImg;
    private long crdate;  //bill's create date
    private boolean income;  //whether is income
    private int version;  //bill's version ,use to revise data

    public ShareBill(TotalBill TotalBill) {
        this.cost = TotalBill.getCost();
        this.content = TotalBill.getContent();
        this.userid = TotalBill.getUserid();
        this.payName = TotalBill.getPayName();
        this.payImg = TotalBill.getPayImg();
        this.sortName = TotalBill.getSortName();
        this.sortImg = TotalBill.getSortImg();
        this.crdate = TotalBill.getCrdate();
        this.income = TotalBill.getIncome();
        this.version = TotalBill.getVersion();
        //不要忘记设置服务器ObjectId
        if (TotalBill.getRid() != null)
            setObjectId(TotalBill.getRid());
    }
    @Generated(hash = 1436563903)
    public ShareBill(String id, float cost, String content, String userid, String payName, String payImg, String sortName,
            String sortImg, long crdate, boolean income, int version) {
        this.id = id;
        this.cost = cost;
        this.content = content;
        this.userid = userid;
        this.payName = payName;
        this.payImg = payImg;
        this.sortName = sortName;
        this.sortImg = sortImg;
        this.crdate = crdate;
        this.income = income;
        this.version = version;
    }
    @Generated(hash = 524624103)
    public ShareBill() {
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShareBill shareBill = (ShareBill) o;
        return Float.compare(shareBill.cost, cost) == 0 &&
                crdate == shareBill.crdate &&
                income == shareBill.income &&
                version == shareBill.version &&
                Objects.equals(id, shareBill.id) &&
                Objects.equals(content, shareBill.content) &&
                Objects.equals(userid, shareBill.userid) &&
                Objects.equals(payName, shareBill.payName) &&
                Objects.equals(payImg, shareBill.payImg) &&
                Objects.equals(sortName, shareBill.sortName) &&
                Objects.equals(sortImg, shareBill.sortImg);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, cost, content, userid, payName, payImg, sortName, sortImg, crdate, income, version);
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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

    public long getCrdate() {
        return crdate;
    }

    public void setCrdate(long crdate) {
        this.crdate = crdate;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean getIncome() {
        return this.income;
    }
}
