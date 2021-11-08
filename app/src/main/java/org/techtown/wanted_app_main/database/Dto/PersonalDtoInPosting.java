package org.techtown.wanted_app_main.database.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PersonalDtoInPosting {

    @SerializedName("id")
    @Expose
    public Long id;
    public String nickname;
    public String img;

    public PersonalDtoInPosting(Long id, String nickname, String img) {
        this.id = id;
        this.nickname = nickname;
        this.img = img;
    }

    @Override
    public String toString() {
        return "PersonalDtoInPosting{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
