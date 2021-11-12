package org.techtown.wanted_app_main.database.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EvaluateDtoInPersonal {


    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("earnest")
    @Expose
    public double earnest; // 성실
    public double teamwork; // 팀워크
    public double contribution; // 기여도
    public int count; // 평가 받은 횟수

    public EvaluateDtoInPersonal(double earnest,double teamwork,double contribution,int count) {
        this.earnest = earnest;
        this.count = count;
        this.teamwork = teamwork;
        this.contribution = contribution;
    }

    @Override
    public String toString() {
        return "PersonalDtoInPosting{" +
                "id=" + id +
                ", earnest='" + earnest + '\'' +
                ", teamwork='" + teamwork + '\'' +
                ", contribution='" + contribution + '\'' +
                ",count='" + count + '\'' +

                '}';
    }
}
