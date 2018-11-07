package com.tcl.easybill.pojo;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String image;
    private String age ;
    private String gender;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
