package me.hacknanjing.util;

/**
 * Created by Vincent on 2017/3/29.
 */

public class UserChangeEvent {
    private boolean login;

    public UserChangeEvent(boolean login) {
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
