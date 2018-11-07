package com.tcl.easybill.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TotalBill {
    @Id(autoincrement = true)
    private Long id;

    private String rid;  //objectID in server
    private float cost;
    private String content;  //remarks in bill
    private String userid;
    private String payName;
    private String payImg;
    private String sortName;  //sort of bill
    private String sortImg;  //
    private long crdate;  //bill's create date
    private boolean income;  //whether is income
    private int version;  //bill's version ,use to revise data

    @Generated(hash = 47599085)
    public TotalBill(Long id, String rid, float cost, String content, String userid,
            String payName, String payImg, String sortName, String sortImg,
            long crdate, boolean income, int version) {
        this.id = id;
        this.rid = rid;
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

    @Generated(hash = 1977995443)
    public TotalBill() {
    }

    @Override
    public String toString() {
        return "TotalBill{" +
                "id=" + id +
                ", rid='" + rid + '\'' +
                ", cost=" + cost +
                ", content='" + content + '\'' +
                ", userid='" + userid + '\'' +
                ", payName='" + payName + '\'' +
                ", payImg='" + payImg + '\'' +
                ", sortName='" + sortName + '\'' +
                ", sortImg='" + sortImg + '\'' +
                ", crdate=" + crdate +
                ", income=" + income +
                ", version=" + version +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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
