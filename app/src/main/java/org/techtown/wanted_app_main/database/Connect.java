package org.techtown.wanted_app_main.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Connect {
    public Long id;
    @SerializedName("posting")
    @Expose
    public Long postingId;
    public Long senderId;

    public Connect(Long id, Long postingId, Long senderId) {
        this.id = id;
        this.postingId = postingId;
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return "Connect{" +
                "id=" + id +
                ", postingId=" + postingId +
                ", senderId=" + senderId +
                '}';
    }
}
