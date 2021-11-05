package org.techtown.wanted_app_main.database;

import java.util.ArrayList;
import java.util.List;

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
    public String career;
    public Integer gender;
    public String img;

    public Personal(Long id, String string_id, String pwd, String nickname, String school, String major, Integer grade, Integer age,
                    String address, String career, Integer gender, String img) {
        this.id = id;
        this.string_id = string_id;
        this.pwd = pwd;
        this.nickname = nickname;
        this.school = school;
        this.major = major;
        this.grade = grade;
        this.age = age;
        this.address = address;
        this.career = career;
        this.gender = gender;
        this.img = img;
    }

}
