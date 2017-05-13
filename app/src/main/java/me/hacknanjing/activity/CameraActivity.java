package me.hacknanjing.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_camera;
    }

    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


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

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}
