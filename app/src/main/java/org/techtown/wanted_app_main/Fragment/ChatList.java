package org.techtown.wanted_app_main.Fragment;

public class ChatList {
    public String nickname;
    public String content;
    public int imgRes;

    public ChatList(String nickname, String content, int imgRes) {
        this.nickname = nickname;
        this.content = content;
        this.imgRes = imgRes;
    }

    public String getNickame() {
        return nickname;
    }

    public void setNickame(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
