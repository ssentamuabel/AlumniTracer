package com.finalyear.project.ApiConf;

import com.finalyear.project.alumnitracerData.User;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private User user;

    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
