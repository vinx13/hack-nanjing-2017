package me.hacknanjing.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import me.hacknanjing.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static me.hacknanjing.util.CameraUtil.getCameraInstance;

/**
 * Created by dynamicheart on 5/13/2017.
 */

public class CameraActivity extends BaseActivity implements SurfaceHolder.Callback {
    @BindView(R.id.camera_preview)
    SurfaceView svPreview;
    @BindView(R.id.origin_photo)
    ImageView originPhoto;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;

    SurfaceHolder svHolder;

    final public static String EXTRA_BG = "EXTRA_BG";
    int extra_bg;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_camera;
    }

    private Camera camera;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extra_bg = getIntent().getIntExtra(EXTRA_BG, -1);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (extra_bg >= 0) {
            Bitmap background = BitmapFactory.decodeResource(getResources(), extra_bg);
            int width = background.getWidth(), height = background.getHeight();
            background.recycle();
            Picasso.with(this).load(extra_bg).rotate(width > height ? 90 : 0).into(originPhoto);

            Picasso.with(this).load(R.drawable.group_photo).into(ivCamera);
        } else {
            Picasso.with(this).load(R.drawable.camera).into(ivCamera);
        }
        camera = getCameraInstance();
       /* camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();
                camera.setParameters(parameters);
            }
        });*/

        // Create our Preview view and set it as the content of our activity.
        svHolder = svPreview.getHolder();
        svHolder.addCallback(this);

        ivCamera.setOnClickListener(v -> {
            camera.takePicture(null, null, (data, camera1) -> {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.outHeight = 640;
                options.inSampleSize = 5;
                BitmapFactory.decodeByteArray(data, 0, data.length, options);

                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                try {
                    String filename = getCacheDir() + "/image.png";
                    File file = new File(filename);
                    FileOutputStream stream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();

                    OkHttpClient client = new OkHttpClient.Builder().build();
                    MediaType mediaType = MediaType.parse("image/png");

                    RequestBody fileBody = RequestBody.create(mediaType, file);

                    RequestBody formBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", file.getName(), fileBody)
                            .build();
                    Request request =
                            new Request.Builder()
                                    .url("http://192.168.2.61:8080/uploadimage")
                                    .post(formBody)
                                    .build();
                    Call call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                            Log.e("error response",e.getMessage());
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String json = response.body().string();
                            Log.d("response", json);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            });
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (svHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            camera.setPreviewDisplay(svHolder);
            camera.startPreview();

        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }


}
