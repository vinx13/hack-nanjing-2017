package me.hacknanjing.activity;

import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import me.hacknanjing.R;
import me.hacknanjing.widget.CameraPreview;

import static me.hacknanjing.util.CameraUtil.getCameraInstance;

/**
 * Created by dynamicheart on 5/13/2017.
 */

public class CameraActivity extends BaseActivity {
    @BindView(R.id.origin_photo)
    ImageView originPhoto;
    @BindView(R.id.button_capture)
    ImageButton buttonCapture;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_camera;
    }

    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_camera);

        Picasso.with(this).load(R.drawable.test_photo).into(originPhoto);
        Picasso.with(this).load(R.drawable.group_photo).into(buttonCapture);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}
