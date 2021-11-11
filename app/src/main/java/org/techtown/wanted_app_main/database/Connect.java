package org.techtown.wanted_app_main.database;

public class Connect {
    public Long id;
    public Long senderId;
    public String nickname;
    public String img;

    public Connect(Long id, Long senderId, String nickname, String img) {
        this.id = id;
        this.senderId = senderId;
        this.nickname = nickname;
        this.img = img;
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
