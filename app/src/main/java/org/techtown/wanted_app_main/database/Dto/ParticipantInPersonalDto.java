package org.techtown.wanted_app_main.database.Dto;

import org.techtown.wanted_app_main.database.Room;

public class ParticipantInPersonalDto {
    public Room room;
    public String nickname;
    public String img;

    public ParticipantInPersonalDto(Room room, String nickname, String img) {
        this.room = room;
        this.nickname = nickname;
        this.img = img;
    }
}
