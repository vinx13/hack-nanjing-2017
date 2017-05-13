package me.hacknanjing.api.model;

import com.google.gson.annotations.SerializedName;

import me.hacknanjing.R;

/**
 * Created by Vincent on 2017/1/16.
 */

public class User {

    private String username;
    private int avatar;

    public User(String username, int avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getAvatar() {

        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
