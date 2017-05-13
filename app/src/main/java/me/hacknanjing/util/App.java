package me.hacknanjing.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;



/**
 * Created by Vincent on 2017/1/10.
 */

public class App extends Application {

    @SuppressWarnings("StaticFieldLeak")
    private static Context context;

    private String installationId;

    public String getInstallationId() {
        return installationId;
    }

    public static Context getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        EventBus.getDefault().register(this);
        UserManager.init();
    }

}
