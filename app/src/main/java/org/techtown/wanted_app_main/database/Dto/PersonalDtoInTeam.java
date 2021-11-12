package org.techtown.wanted_app_main.database.Dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalDtoInTeam implements Parcelable {

    @SerializedName("id")
    @Expose
    public Long personalId;
    public String nickname;
    public String img;
    public String school;
    public String major;
    public String address;

    public PersonalDtoInTeam(Long personalId, String nickname, String img, String school, String major, String address) {
        this.personalId = personalId;
        this.nickname = nickname;
        this.img = img;
        this.school = school;
        this.major=major;
        this.address=address;
    }

    protected PersonalDtoInTeam(Parcel in) {
        if (in.readByte() == 0) {
            personalId = null;
        } else {
            personalId = in.readLong();
        }
        nickname = in.readString();
        img = in.readString();
        school = in.readString();
        major = in.readString();
        address = in.readString();
    }

    public static final Creator<PersonalDtoInTeam> CREATOR = new Creator<PersonalDtoInTeam>() {
        @Override
        public PersonalDtoInTeam createFromParcel(Parcel in) {
            return new PersonalDtoInTeam(in);
        }

        @Override
        public PersonalDtoInTeam[] newArray(int size) {
            return new PersonalDtoInTeam[size];
        }
    };

    @Override
    public String toString() {
        return "PersonalDtoInTeam{" +
                "id=" + personalId +
                ", nickname='" + nickname + '\'' +
                ", img='" + img + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (personalId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(personalId);
        }
        dest.writeString(nickname);
        dest.writeString(img);
        dest.writeString(school);
        dest.writeString(major);
        dest.writeString(address);
    }
}
