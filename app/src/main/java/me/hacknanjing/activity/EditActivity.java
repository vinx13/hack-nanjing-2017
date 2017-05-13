package me.hacknanjing.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import me.hacknanjing.R;

/**
 * Created by Vincent on 2017/5/14.
 */

public class EditActivity extends BaseActivity {
    final public static String EXTRA_IMAGE = "EXTRA_IMAGE";
    @BindView(R.id.iv_body)
    ImageView ivBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra(EXTRA_IMAGE);
        if (url != null) {
            Picasso.with(this).load(url).into(ivBody);
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_edit;
    }
}
