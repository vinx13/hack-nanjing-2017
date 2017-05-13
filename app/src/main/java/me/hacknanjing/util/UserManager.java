package me.hacknanjing.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;


import me.hacknanjing.api.model.User;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vincent on 2017/1/18.
 */

public class UserManager {
    static private UserManager instance;
    private User user;
    private boolean login = false;

    private UserManager() {
    }

    public static UserManager getInstance() {
        return instance;
    }

    public static void init() {
        instance = new UserManager();
    }

    public boolean isLogin() {
        return login;
    }

    public User getUser() {
        return user;
    }

    public void login(User user) {
        this.user = user;
        login = true;
        Log.d("EventBus", "UserChange true");
        EventBus.getDefault().post(new UserChangeEvent(true));
    }

    public void logout() {
        user = null;
        login = false;
        Log.d("EventBus", "UserChange false");
        EventBus.getDefault().post(new UserChangeEvent(false));
    }
}
