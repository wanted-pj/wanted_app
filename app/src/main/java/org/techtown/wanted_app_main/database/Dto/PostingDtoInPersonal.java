package org.techtown.wanted_app_main.database.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.techtown.wanted_app_main.database.Connect;
import org.techtown.wanted_app_main.database.Team;

import java.util.ArrayList;

public class PostingDtoInPersonal {

    public Long id;
    @SerializedName("personal")
    @Expose
    public Long personalId;
    public String category;
    public String title;
    public String content;
    public String postingTime;
    public ArrayList<Connect> connects;
    public PostingDtoInPersonal(Long id, Long personalId, String category, String title, String content, Team team, String postingTime, ArrayList<Connect> connects) {
        this.id = id;
        this.personalId = personalId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.postingTime = postingTime;
        this.connects = connects;
    }

    @Override
    public String toString() {
        return "PostingDtoInPersonal{" +
                "id=" + id +
                ", personal=" + personalId +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", postingTime='" + postingTime + '\'' +
                ", connects=" + connects +
                '}';
    }
}

