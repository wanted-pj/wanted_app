package org.techtown.wanted_app_main.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.techtown.wanted_app_main.database.Dto.PostingDtoInPersonal;

import java.util.ArrayList;

public class Personal {
    @SerializedName("id")
    @Expose
    public Long id;
    public String stringId;
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
    @SerializedName("postings")
    @Expose
    public ArrayList<PostingDtoInPersonal> postings;

    public Personal(Long id, String stringId, String pwd, String nickname, String school, String major, Integer grade, Integer age,
                    String address, String career, Integer gender, String img, ArrayList postings) {
        this.id = id;
        this.stringId = stringId;
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
        if (postings != null) {
            this.postings = postings;
        } else{
            postings = new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        return "Personal{" +
                "id=" + id +
                ", stringId='" + stringId + '\'' +
                ", pwd='" + pwd + '\'' +
                ", nickname='" + nickname + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", grade=" + grade +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", career='" + career + '\'' +
                ", gender=" + gender +
                ", img='" + img + '\'' +
                ", postings='" + postings +
                '}';
    }
}
