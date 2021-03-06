package org.techtown.wanted_app_main.database;

public class Friend {
    public Long id;
    public String name;
    public String school;
    public String major;
    public String address;
    public int imgRes;

    public Friend(Long id, String name, String school, String major, String address, int imgRes) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.major = major;
        this.address = address;
        this.imgRes = imgRes;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
