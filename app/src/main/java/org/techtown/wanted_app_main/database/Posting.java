package org.techtown.wanted_app_main.database;

public class Posting {
    public Long posting_id;
    public String category;
    public String title;
    public String content;
    public Integer personal_id;

    public Posting(Long posting_id, String category, String title, String content, Integer personal_id) {
        this.posting_id = posting_id;
        this.category = category;
        this.title = title;
        this.content = content;
        this.personal_id = personal_id;
    }
}
