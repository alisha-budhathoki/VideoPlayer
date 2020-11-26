package com.halfwaiter.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;

public class CustomCameraActivity extends AppCompatActivity {
    Camera camera;
    FrameLayout frameLayout;
    ShowCamera showCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_camera);
        frameLayout = findViewById(R.id.frameLayout);
        System.out.println("xmdksidj");

        //open camera
        camera = Camera.open();
        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);
    }
}