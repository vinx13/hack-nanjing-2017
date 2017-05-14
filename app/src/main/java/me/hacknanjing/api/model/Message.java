package me.hacknanjing.api.model;

/**
 * Created by dynamicheart on 5/14/2017.
 */

public class Message {
    public static final int TYPE_RECEIVED = 0;

    public static final int TYPE_SENT = 1;

    public static final int CONTENT_STRING = 0;

    public static final int CONTENT_PHOTO = 1;

    private User user;

    private String content;

    private int photo;

    private int type;

    private int contentType;

    public Message(User user, String content, int photo, int type, int contentType) {
        this.user = user;
        this.content = content;
        this.photo = photo;
        this.type = type;
        this.contentType = contentType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
}
