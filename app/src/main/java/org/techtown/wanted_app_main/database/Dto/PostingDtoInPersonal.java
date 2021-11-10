package org.techtown.wanted_app_main.database.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.techtown.wanted_app_main.database.Connect;
import org.techtown.wanted_app_main.database.Team;

import java.util.ArrayList;

public class PostingDtoInPersonal {

    public Long postingId;
    public String category;
    public String title;
    public String content;
    public String postingTime;
    public ArrayList<Connect> connects;
    public String teamName;

    public PostingDtoInPersonal(Long postingId, String category, String title, String content, String postingTime, ArrayList<Connect> connects, String teamName) {
        this.postingId = postingId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.postingTime = postingTime;
        this.connects = connects;
        this.teamName = teamName;
    }
}

