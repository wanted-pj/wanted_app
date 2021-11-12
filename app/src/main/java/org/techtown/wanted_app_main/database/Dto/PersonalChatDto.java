package org.techtown.wanted_app_main.database.Dto;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PersonalChatDto {
    @SerializedName("id")
    @Expose
    public Long personalId;
    public ArrayList<ParticipantInPersonalDto> participants;

    public PersonalChatDto(Long id, ArrayList participants) {
        this.personalId = id;
        this.participants = participants;
    }
}
