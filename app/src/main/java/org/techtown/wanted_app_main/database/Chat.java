package org.techtown.wanted_app_main.database;

public class Chat {
    public String content;
    public String time;
    public int type;

    public Chat(String content, String time, int type) {
        this.content = content;
        this.time = time;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
