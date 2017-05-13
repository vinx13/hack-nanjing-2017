package me.hacknanjing.util;

import android.widget.Toast;

/**
 * Created by Vincent on 2017/1/20.
 */
public class ToastUtils {

    public static void showShort(int resId) {
        Toast.makeText(App.getInstance(), resId, Toast.LENGTH_SHORT).show();
    }


    public static void showShort(String message) {
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show();
    }


    public static void showLong(int resId) {
        Toast.makeText(App.getInstance(), resId, Toast.LENGTH_LONG).show();
    }


    public static void showLong(String message) {
        Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG).show();
    }

}
