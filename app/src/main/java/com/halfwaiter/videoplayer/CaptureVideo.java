package com.halfwaiter.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CaptureVideo extends Activity {

    private VideoCapture videoCapture;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_video);
        videoCapture = (VideoCapture) findViewById(R.id.videoView);
        stop= (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                videoCapture.stopCapturingVideo();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

    }
}

