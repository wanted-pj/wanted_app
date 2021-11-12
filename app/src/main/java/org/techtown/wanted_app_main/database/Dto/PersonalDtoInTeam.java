package org.techtown.wanted_app_main.database.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalDtoInTeam {

    @SerializedName("id")
    @Expose
    public Long personalId;
    public String nickname;
    public String img;
    public String school;
    public String major;
    public String address;

    public PersonalDtoInTeam(Long personalId, String nickname, String img, String school, String major, String address) {
        this.personalId = personalId;
        this.nickname = nickname;
        this.img = img;
        this.school = school;
        this.major=major;
        this.address=address;
    }

    @Override
    public String toString() {
        return "PersonalDtoInTeam{" +
                "id=" + personalId +
                ", nickname='" + nickname + '\'' +
                ", img='" + img + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
