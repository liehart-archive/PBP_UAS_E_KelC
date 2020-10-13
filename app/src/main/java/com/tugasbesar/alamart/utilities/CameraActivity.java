package com.tugasbesar.alamart.utilities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.tugasbesar.alamart.R;

public class CameraActivity extends AppCompatActivity {

    private Camera mCamera = null;
    private CameraView mCameraView = null;
    private MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);
        }

        button = findViewById(R.id.btnTake);

        try {
            mCamera= android.hardware.Camera.open();
        }catch (Exception e){
            Log.d("error", "Failed to get camera"+ e.getMessage());
        }

        if(mCamera!=null){
            mCameraView= new CameraView(this, mCamera);
            FrameLayout camera_view = findViewById(R.id.FLCamera);
            camera_view.addView(mCameraView);
        }

        ImageButton imageClose = findViewById(R.id.imgClose);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}