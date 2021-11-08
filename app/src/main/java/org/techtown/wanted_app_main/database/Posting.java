package org.techtown.wanted_app_main.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.techtown.wanted_app_main.database.Dto.PersonalDtoInPosting;

import java.util.List;

public class Posting implements Parcelable {
    public Long id;
    @SerializedName("personal")
    @Expose
    public PersonalDtoInPosting personal;
    public String category;
    public String title;
    public String content;
    public List<Connect> connects;
    public String postingTime;

    public Posting(Long id, PersonalDtoInPosting personal, String category, String title, String content, List<Connect> connects, String postingTime) {
        this.id = id;
        this.personal = personal;
        this.category = category;
        this.title = title;
        this.content = content;
        this.connects = connects;
        this.postingTime = postingTime;
    }

    @Override
    public String toString() {
        return "Posting{" +
                "posting_id=" + id +
                ", personal=" + personal +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", connects=" + connects +
                ", postingTime='" + postingTime + '\'' +
                '}';
    }

    public Posting(Parcel source) {
        id = source.readLong();
        category = source.readString();
        title = source.readString();
        content = source.readString();
        postingTime = source.readString();
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
        dest.writeLong(id);
        dest.writeString(category);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(postingTime);
    }
}
