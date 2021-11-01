package org.techtown.wanted_app_main.Fragment;

import java.io.Serializable;

public class Friend {
    public String name;
    public String school;
    public String major;
    public int imgRes;

    public Friend(String name, String school, String major, int imgRes) {
        this.name = name;
        this.school = school;
        this.major = major;
        this.imgRes = imgRes;
    }
    //dfsdd
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgUrl) {
        this.imgRes = imgUrl;
    }
}
