package com.halfwaiter.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CaptureVideo extends Activity {

    private VideoCapture videoCapture;
    private Button stop;
    private View mToggleButton;
    TimerThread mTimer;
    int mCount;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_video);
        videoCapture = (VideoCapture) findViewById(R.id.videoView);
        mToggleButton= (Button) findViewById(R.id.stop);
        message = findViewById(R.id.msg);
        mTimer= new TimerThread();
        mTimer.setOnAlarmListener(mSTimer_OnAlarm);
        mTimer.setPeriod(1000);


        mToggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (((ToggleButton) v).isChecked()) {
                    System.out.println("jnsab");
                    videoCapture.startCapturingVideo();
                    counterIncrease();
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
    OnAlarmListener mSTimer_OnAlarm= new OnAlarmListener() {
        @Override
        public void OnAlarm(TimerThread source) {
            mCount--;
            message.setText("Count="+mCount);
            if( mCount==0) source.stop();
        }
    };
    private void counterIncrease() {
        System.out.println("sbdsb");
        mCount = 30;
        mTimer.start();

    }
}

