package me.hacknanjing.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vincent on 2017/1/16.
 */

public class User {
    @SerializedName("uid")
    private String uid;


    public String getUid() {
        return uid;
    }

}
