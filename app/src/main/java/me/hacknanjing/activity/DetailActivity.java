package me.hacknanjing.activity;

import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import me.hacknanjing.R;
import me.hacknanjing.api.model.Post;
import me.hacknanjing.util.Factory;

/**
 * Created by Vincent on 2017/5/14.
 */

public class DetailActivity extends BaseActivity {
    final public static String EXTRA_POST_INDEX = "EXTRA_POST_INDEX";
    final public static String EXTRA_IS_FRIENDS = "EXTRA_IS_FRIENDS";
    int index;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.iv_body)
    ImageView ivBody;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getIntent().getIntExtra(EXTRA_POST_INDEX, 0);
        boolean is_friends = getIntent().getBooleanExtra(EXTRA_POST_INDEX, true);
        if (is_friends) {
            post = Factory.getInstance().getAllFriendPosts().get(index);
        } else {
            post = Factory.getInstance().getAllPosts().get(index);
        }
        ivAvatar.setImageResource(post.getUser().getAvatar());
        ivBody.setImageResource(post.getImage());
        ivCamera.setOnClickListener(v -> {
            Intent intent = new Intent(this, CameraActivity.class);
            intent.putExtra(CameraActivity.EXTRA_BG, post.getImage());
            startActivity(intent);
        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_detail;
    }
}

