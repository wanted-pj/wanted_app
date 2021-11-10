package org.techtown.wanted_app_main.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Connect {
    public Long id;
    public Long senderId;
    public String nickName;
    public String img;

    public Connect(Long id, Long senderId, String nickName, String img) {
        this.id = id;
        this.senderId = senderId;
        this.nickName = nickName;
        this.img = img;
    }

    @Override
    public String toString() {
        return "Connect{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", nickName='" + nickName + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
