package me.hacknanjing.api.model;

import com.amap.api.maps2d.model.LatLng;

/**
 * Created by Vincent on 2017/5/13.
 */

public class Post {
    User user;
    int image;
    Boolean liked;
    String content;
    LatLng position;


    public Post(User user, int image, String content, LatLng position) {
        this.user = user;
        this.image = image;
        this.content = content;
        this.liked = false;
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
