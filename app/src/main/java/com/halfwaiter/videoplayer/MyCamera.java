package com.halfwaiter.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.VideoView;

public class MyCamera extends Activity implements
        SurfaceHolder.Callback, MediaRecorder.OnInfoListener, MediaRecorder.OnErrorListener {
    private MediaRecorder recorder;
    Camera camera;
    private SurfaceHolder holder;
    VideoView videoView;
    TextView msg;
    ToggleButton startStop;
    int mCount;
    TimerThread mTimer;
    int maxDuration = 7000;//7sec
    int frameRate = 1;//15

    public static String videoPath = Environment.getExternalStorageDirectory()
            .getPath() + "/YOUR_VIDEO.mp4";

    @SuppressLint("NewApi")
    public boolean init() {
        try {
            recorder = new MediaRecorder();
            holder = videoView.getHolder();
            holder.addCallback(this);
            System.out.println("sdjbs");
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            camera = Camera.open();
            if (android.os.Build.VERSION.SDK_INT > 7)
                camera.setDisplayOrientation(90);
            System.out.println("sdnjsd");
            camera.unlock();
            System.out.println("camera unlocked");
            recorder.setCamera(camera);
            recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
            recorder.setOutputFile(videoPath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_camera);

        videoView = findViewById(R.id.videoView1);
        msg = findViewById(R.id.messageTxt);
        startStop= findViewById(R.id.start);

        mTimer= new TimerThread();
        mTimer.setOnAlarmListener(mSTimer_OnAlarm);
        mTimer.setPeriod(1000);



        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            counterIncrease();

            }
        });


    }

    private void counterIncrease() {
        mCount = 30;
        mTimer.start();

    }
    protected void onResume(){
        System.out.println("in on resume");
        super.onResume();
        if(!init())
            finish();
    }

    @Override
    public void onError(MediaRecorder mr, int what, int extra) {

    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            System.out.println("dsnjbd");
//            recorder.setProfile(CamcorderProfile.get(-1,CamcorderProfile.QUALITY_HIGH));
//            recorder.setPreviewDisplay(holder.getSurface());
            recorder.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    OnAlarmListener mSTimer_OnAlarm= new OnAlarmListener() {
        @Override
        public void OnAlarm(TimerThread source) {
            mCount--;
            msg.setText("Count="+mCount);
            if( mCount==0) source.stop();
        }
    };
}