package com.tcl.easybill.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Objects;
import org.greenrobot.greendao.annotation.Generated;

import cn.bmob.v3.BmobUser;

@Entity
public class Person extends BmobUser{
    @Id
    private int ID;
    private String NAME;
    private int PASSWORD;
    private int gender;
    private int phonenumber;
    private String budget;
    private int shareid;

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", NAME='" + NAME + '\'' +
                ", PASSWORD=" + PASSWORD +

                ", gender=" + gender +
                ", phonenumber=" + phonenumber +
                ", budget=" + budget +
                ", shareid=" + shareid +
                '}';
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public int getShareid() {
        return shareid;
    }

    public void setShareid(int shareid) {
        this.shareid = shareid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return ID == person.ID &&
                PASSWORD == person.PASSWORD &&
                gender == person.gender &&
                phonenumber == person.phonenumber &&
                shareid == person.shareid &&
                Objects.equals(NAME, person.NAME) &&
                Objects.equals(budget, person.budget);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ID, NAME, PASSWORD, gender, phonenumber, budget, shareid);
    }



    @Generated(hash = 1417542635)
    public Person(int ID, String NAME, int PASSWORD, int gender, int phonenumber,
            String budget, int shareid) {
        this.ID = ID;
        this.NAME = NAME;
        this.PASSWORD = PASSWORD;
        this.gender = gender;
        this.phonenumber = phonenumber;
        this.budget = budget;
        this.shareid = shareid;
    }

    @Generated(hash = 1024547259)
    public Person() {
    }

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public int getPASSWORD() {
        return PASSWORD;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setPASSWORD(int PASSWORD) {
        this.PASSWORD = PASSWORD;
    }


}
