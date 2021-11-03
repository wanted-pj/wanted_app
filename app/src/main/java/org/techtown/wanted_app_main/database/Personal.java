package org.techtown.wanted_app_main.database;

public class Personal {
    public Long id;
    public String string_id;
    public String pwd;
    public String nickname;
    public String school;
    public String major;
    public Integer grade;
    public Integer age;
    public String address;
    public String carrer;
    public Integer gender;
    public String img;

    public Personal(Long id, String string_id, String pwd, String nickname, String school, String major, Integer grade, Integer age, String address, String carrer, Integer gender, String img) {
        this.id = id;
        this.string_id = string_id;
        this.pwd = pwd;
        this.nickname = nickname;
        this.school = school;
        this.major = major;
        this.grade = grade;
        this.age = age;
        this.address = address;
        this.carrer = carrer;
        this.gender = gender;
        this.img = img;
    }
}
