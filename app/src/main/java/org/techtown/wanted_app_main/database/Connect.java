package org.techtown.wanted_app_main.database;

public class Connect {
    public Long id;
    public Long senderId;
    public String nickname;
    public String img;
    public Boolean result; // 0이면 아직 결정 안됨 1 수락됨 2 거절됨

    public Connect(Long id, Long senderId, String nickname, String img, Boolean result) {
        this.id = id;
        this.senderId = senderId;
        this.nickname = nickname;
        this.img = img;
        this.result = result;
    }

    @Override
    public String toString() {
        return "Connect{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", nickname='" + nickname + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
