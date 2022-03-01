package com.example.apitorecyclerview;

import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("title")
    private String title;
    @SerializedName("message")
    private String message;

    public Data(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
