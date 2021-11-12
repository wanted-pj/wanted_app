package org.techtown.wanted_app_main.database;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.techtown.wanted_app_main.database.Dto.PersonalDtoInTeam;

import java.util.ArrayList;

public class Team implements Parcelable {
    public Long teamId;
    public String teamName;
    public Long leaderId;
    @SerializedName("personals")
    @Expose
    public ArrayList<PersonalDtoInTeam> personals;

    public Team(Long teamId, String teamName, Long leaderId, ArrayList personals) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.leaderId=leaderId;
        if (personals != null) {
            this.personals = personals;
        }
    }


    protected Team(Parcel in) {
        if (in.readByte() == 0) {
            teamId = null;
        } else {
            teamId = in.readLong();
        }
        teamName = in.readString();
        if (in.readByte() == 0) {
            leaderId = null;
        } else {
            leaderId = in.readLong();
        }
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (teamId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(teamId);
        }
        dest.writeString(teamName);
        if (leaderId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(leaderId);
        }
    }
}
