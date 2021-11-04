package org.techtown.wanted_app_main.Fragment.Chatting;

public class Comment {

    String uid;
    String message;
    Object timestamp;

    public Comment(String uid,String message, Object timestamp){
        this.uid=uid;
        this.message=message;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void  setUid(String uid) {
        this.uid = uid;
    }


    public String getMessage() {
        return message;
    }

    public void  setTitle(String title) {
        this.message = message;
    }


    public Object getTimestamp() {
        return timestamp;
    }
    public void  setTimestamp(String timestamp) {
        this.timestamp = timestamp;
}}
