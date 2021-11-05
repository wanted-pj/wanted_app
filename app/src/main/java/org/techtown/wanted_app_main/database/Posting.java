package org.techtown.wanted_app_main.database;

import java.util.List;

public class Posting {
    public Long posting_id;
    public Personal personal;
    public String category;
    public String title;
    public String content;
    public List<Connect> connects;
    public Team team;
    public String postingTime;

    public Posting(Long posting_id, Personal personal, String category, String title, String content, List<Connect> connects, Team team, String postingTime) {
        this.posting_id = posting_id;
        this.personal = personal;
        this.category = category;
        this.title = title;
        this.content = content;
        this.connects = connects;
        this.team = team;
        this.postingTime = postingTime;
    }
}
