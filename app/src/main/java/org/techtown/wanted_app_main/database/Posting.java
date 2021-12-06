package org.techtown.wanted_app_main.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.List;

public class Posting implements Parcelable {
    public Long postingId;
//    @SerializedName("personalId")
//    @Expose
    public Long personalId;
    public String category;
    public String title;
    public String content;
    public List<Connect> connects;
    public String postingTime;
    public String endTime;
    public String teamName;
    public String nickname;
    public String img;
    public boolean checkRecruiting;

    public Posting(Long postingId, Long personalId, String category, String title, String content, List<Connect> connects, String postingTime, String endTime, String teamName, String nickname, String img, boolean checkRecruiting) {
        this.postingId = postingId;
        this.personalId = personalId;
        this.category = category;
        this.title = title;
        this.content = content;
        this.connects = connects;
        this.postingTime = postingTime;
        this.endTime = endTime;
        this.teamName = teamName;
        this.nickname = nickname;
        this.img = img;
        this.checkRecruiting = checkRecruiting;
    }

    @Override
    public String toString() {
        return "Posting{" +
                "postingId=" + postingId +
                ", personalId=" + personalId +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", connects=" + connects +
                ", postingTime='" + postingTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", teamName='" + teamName + '\'' +
                ", nickname='" + nickname + '\'' +
                ", img='" + img + '\'' +
                ", checkRecruiting=" + checkRecruiting +
                '}';
    }

    public Posting(Parcel source) {
        postingId = source.readLong();
        category = source.readString();
        title = source.readString();
        content = source.readString();
//        postingTime = source.
        teamName = source.readString();
    }

    public static final Creator<Posting> CREATOR = new Creator<Posting>() {
        @Override
        public Posting createFromParcel(Parcel source) {
            return new Posting(source);
        }

        @Override
        public Posting[] newArray(int size) {
            return new Posting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(postingId);
        dest.writeString(category);
        dest.writeString(title);
        dest.writeString(content);
//        dest.writeString(postingTime);
        dest.writeString(teamName);
    }
}
