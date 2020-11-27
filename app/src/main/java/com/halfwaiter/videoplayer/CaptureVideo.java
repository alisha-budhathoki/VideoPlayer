package com.halfwaiter.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class CaptureVideo extends Activity {

    private VideoCapture videoCapture;
    private Button stop;
    private View mToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_video);
        videoCapture = (VideoCapture) findViewById(R.id.videoView);
        mToggleButton= (Button) findViewById(R.id.stop);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (((ToggleButton) v).isChecked()) {
                    System.out.println("jnsab");
                    videoCapture.startCapturingVideo();
                }
                else {
                    System.out.println("nksaksa");
                    videoCapture.stopCapturingVideo();
                setResult(Activity.RESULT_OK);
                finish();
                }
//
            }
        });

    }
}

