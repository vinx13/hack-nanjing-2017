package me.hacknanjing.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vincent on 2017/1/16.
 */

public class User {
    @SerializedName("uid")
    private String uid;

private String username;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

}
