package org.techtown.wanted_app_main.Fragment;

public class Detail_Friend {
    public String name;
    public String school;
    public String major;
    public String grade;
    public String gender;
    public int imgRes;

    public Detail_Friend(String name, String school, String major,String grade,String gender, int imgRes) {
        this.name = name;
        this.school = school;
        this.major = major;
        this.grade=grade;
        this.gender=gender;
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

    public String getMajor() { return major; }

    public void setMajor(String major) { this.major = major; }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgUrl) {
        this.imgRes = imgUrl;
    }

    public String getGrade() { return grade; }

    public void setGrade(String grade) { this.grade = grade; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }
}
