package org.techtown.wanted_app_main.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.techtown.wanted_app_main.database.Dto.EvaluateDtoInPersonal;
import org.techtown.wanted_app_main.database.Dto.PostingDtoInPersonal;
import java.util.ArrayList;


public class Personal implements Parcelable, Cloneable {
    @SerializedName("id")
    @Expose
    public Long id;
    public String stringId;
    public String pwd;
    public String nickname;
    public String school;
    public String major;
    public Integer grade;
    public Integer age;
    public String address;
    public String career;
    public Integer gender;
    public String img;
    @SerializedName("postings")
    @Expose
    public ArrayList<PostingDtoInPersonal> postings;

    @SerializedName("evaluation")
    @Expose
    public EvaluateDtoInPersonal evaluation;
    public Personal(Long id, String stringId, String pwd, String nickname, String school, String major, Integer grade, Integer age,
                    String address, String career, Integer gender, String img, ArrayList postings,EvaluateDtoInPersonal evaluation ) {
        this.id = id;
        this.stringId = stringId;
        this.pwd = pwd;
        this.nickname = nickname;
        this.school = school;
        this.major = major;
        this.grade = grade;
        this.age = age;
        this.address = address;
        this.career = career;
        this.gender = gender;
        this.img = img;
        if (postings != null) {
            this.postings = postings;
        }
        if (evaluation != null) {
            this.evaluation = evaluation;
        }
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Personal(Parcel source) {
        id = source.readLong();
        stringId = source.readString();
        pwd = source.readString();
        nickname = source.readString();
        school = source.readString();
        major = source.readString();
        grade = source.readInt();
        age = source.readInt();
        address = source.readString();
        career = source.readString();
        gender = source.readInt();
        img = source.readString();
    }

    @Override
    public String toString() {
        return "Personal{" +
                "id=" + id +
                ", stringId='" + stringId + '\'' +
                ", pwd='" + pwd + '\'' +
                ", nickname='" + nickname + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", grade=" + grade +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", career='" + career + '\'' +
                ", gender=" + gender +
                ", img='" + img + '\'' +
                ", postings='" + postings +
                '}';
    }


    public static final Creator<Personal> CREATOR = new Creator<Personal>() {
        @Override
        public Personal createFromParcel(Parcel source) {
            return new Personal(source);
        }

        @Override
        public Personal[] newArray(int size) {
            return new Personal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(stringId);
        dest.writeString(pwd);
        dest.writeString(nickname);
        dest.writeString(school);
        dest.writeString(major);
        dest.writeInt(grade);
        dest.writeInt(age);
        dest.writeString(address);
        dest.writeString(career);
        dest.writeInt(gender);
        dest.writeString(img);
    }

    public Long getId() {
        return id;
    }

    public String getStringId() {
        return stringId;
    }

    public String getPwd() {
        return pwd;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSchool() {
        return school;
    }

    public String getMajor() {
        return major;
    }

    public Integer getGrade() {
        return grade;
    }

    public Integer getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getCareer() {
        return career;
    }

    public Integer getGender() {
        return gender;
    }

    public String getImg() {
        return img;
    }

    public ArrayList<PostingDtoInPersonal> getPostings() {
        return postings;
    }
}
