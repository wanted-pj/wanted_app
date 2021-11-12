package org.techtown.wanted_app_main.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Room {
    @SerializedName("id")
    @Expose
    public Long roomId;
    public ArrayList<Message> messages;

    public Room(Long id, ArrayList messages) {
        this.roomId = id;
        this.messages = messages;
    }
}
