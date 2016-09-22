package com.example.a328789.compress;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 328789 on 2016/9/20.
 */
public class ZxingScanActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener, Camera.PictureCallback {
    private boolean hassurface;
    private Camera camera;
    @BindView(R.id.button_picture)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_zxingscan);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hassurface=false;
        ButterKnife.bind(this);
        button.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();

        SurfaceView surface = (SurfaceView) findViewById(R.id.surface);
        SurfaceHolder holder = surface.getHolder();
        if(hassurface){
            initCamera(holder);
        }else{
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(!hassurface){
            hassurface=true;
            initCamera(holder);
        }
    }

    private void initCamera(SurfaceHolder holder) {
        try {
            if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                camera = Camera.open();

                Camera.Parameters parameters = camera.getParameters();
//                parameters.setAutoExposureLock(true);
//                parameters.setPictureFormat(ImageFormat.RGB_565);
                camera.setDisplayOrientation(90);
                camera.setParameters(parameters);
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            }else{

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hassurface=false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(camera!=null){
            camera.release();
            camera=null;

        }
    }

    @Override
    public void onClick(View v) {
        camera.takePicture(null,null,ZxingScanActivity.this);
        switch (v.getId()){
            case R.id.button_picture:
                camera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        camera.takePicture(null,null,ZxingScanActivity.this);
                    }
                });
                break;
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        File file = new File("/sdcard/aa_Temp/" + System.currentTimeMillis() + ".jpg");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
