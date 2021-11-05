package org.techtown.wanted_app_main.Fragment;

public class BoardDetail {
    public String writer;
    public int imgRes;

    public BoardDetail(String writer, int imgRes){
        this.writer=writer;
        this.imgRes=imgRes;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
