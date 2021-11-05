package org.techtown.wanted_app_main.Fragment;

public class Board {
    public String category;
    public String title;
    public String writer;
    public int imgRes;

    public Board(String category, String title, String writer, int imgRes) {
        this.category = category;
        this.title = title;
        this.writer = writer;
        this.imgRes = imgRes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
