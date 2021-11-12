package org.techtown.wanted_app_main.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {
    @SerializedName("id")
    @Expose
    public Long messageId;
    public Long senderId;
    public String content;
    public boolean readCheck;
    public String messagingTime;

    public Message(Long messageId, Long senderId, String content, boolean readCheck, String messagingTime) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.content = content;
        this.readCheck = readCheck;
        this.messagingTime = messagingTime;
    }
}
