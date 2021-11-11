package org.techtown.wanted_app_main.database.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PersonalDtoInTeam {

    @SerializedName("id")
    @Expose
    public Long id;
    public String nickname;
    public String img;
    public String school;
    public String major;
    public String address;

    public PersonalDtoInTeam(Long id, String nickname, String img,String school,String major,String address) {
        this.id = id;
        this.nickname = nickname;
        this.img = img;
        this.school = school;
        this.major=major;
        this.address=address;


    }

    @Override
    public String toString() {
        return "PersonalDtoInTeam{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", img='" + img + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
