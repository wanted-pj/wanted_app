package org.techtown.wanted_app_main.database.Dto;

import android.os.Parcel;
import android.os.Parcelable;

import org.techtown.wanted_app_main.database.Room;

public class ParticipantInPersonalDto implements Parcelable {
    public Room room;
    public String nickname;
    public String img;
    public Long theOtherPersonalId;
    public String lastMessageTime;

    public ParticipantInPersonalDto(Room room, String nickname, String img, Long theOtherPersonalId ,String lastMessageTime) {
        this.room = room;
        this.nickname = nickname;
        this.img = img;
        this.theOtherPersonalId = theOtherPersonalId;
        this.lastMessageTime = lastMessageTime;
    }

    protected ParticipantInPersonalDto(Parcel in) {
        nickname = in.readString();
        img = in.readString();
        if (in.readByte() == 0) {
            theOtherPersonalId = null;
        } else {
            theOtherPersonalId = in.readLong();
        }
        lastMessageTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickname);
        dest.writeString(img);
        if (theOtherPersonalId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(theOtherPersonalId);
        }
        dest.writeString(lastMessageTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParticipantInPersonalDto> CREATOR = new Creator<ParticipantInPersonalDto>() {
        @Override
        public ParticipantInPersonalDto createFromParcel(Parcel in) {
            return new ParticipantInPersonalDto(in);
        }

        @Override
        public ParticipantInPersonalDto[] newArray(int size) {
            return new ParticipantInPersonalDto[size];
        }
    };
}
