package me.hacknanjing.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Brotherjing on 2016/3/5.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private CompositeSubscription compositeSubscription;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        //setStatusBarTransparent();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (this.compositeSubscription != null) {
            this.compositeSubscription.unsubscribe();
        }
    }

    abstract protected int getContentViewId();

    public CompositeSubscription getCompositeSubscription() {
        if (this.compositeSubscription == null) {
            this.compositeSubscription = new CompositeSubscription();
        }

        return this.compositeSubscription;
    }


    public void addSubscription(Subscription s) {
        if (this.compositeSubscription == null) {
            this.compositeSubscription = new CompositeSubscription();
        }

        this.compositeSubscription.add(s);
    }

    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }
}
