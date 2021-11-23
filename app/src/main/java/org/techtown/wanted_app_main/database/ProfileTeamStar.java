package org.techtown.wanted_app_main.database;

public class ProfileTeamStar {

    public String name;
    public int imgRes;

    public ProfileTeamStar(String name, int imgRes) {
        this.name = name;
        this.imgRes = imgRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

}
