package com.halfwaiter.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class CaptureMyVideo extends AppCompatActivity implements SurfaceHolder.Callback {
    private Button btnRecord;
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private Camera camera;
    private MediaRecorder mr;
    private Boolean isRecording = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_my_video);
        btnRecord = findViewById(R.id.btn_record);
        surfaceView = findViewById(R.id.surfaceViewCap);
        holder = surfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("nasv");
                if (isRecording){
                    System.out.println("dsakjsja");
                    mr.stop();
                    releaseMediaRecorder();
                    camera.lock();
                    btnRecord.setText("Start Video");
                    isRecording = false;
                    Toast.makeText(getApplicationContext(), "Done!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (prepareForVideoRecording()){
                        System.out.println("dsanbhsd");
                        mr.start();
                        btnRecord.setText("Stop Video");
                        isRecording = true;
                    }
                }
            }
        });
    }

    private boolean prepareForVideoRecording() {
        System.out.println("dsjk");
        camera.unlock();
        mr = new MediaRecorder();
        mr.setCamera(camera);
        mr.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mr.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mr.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mr.setOutputFile(getOutputFile(MEDIA_TYPE_VIDEO).toString());
        mr.setPreviewDisplay(holder.getSurface());
        mr.setVideoSize(1920, 1080);
        try {
            mr.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            releaseMediaRecorder();
            return false;
        }
        return false;
    }

    private void releaseMediaRecorder() {
        if (mr != null){
            mr.reset();
            mr.release();
            mr = null;
            camera.lock();
        }
    }

    private String currentTimeStamp(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTime = simpleDateFormat.format(new Date());
        return currentTime;
    }
    private Object getOutputFile(int mediaTypeVideo) {
        File dir = Environment.getExternalStorageDirectory();
        String timeStamp = currentTimeStamp();
        if (mediaTypeVideo == MEDIA_TYPE_VIDEO){
            return new File(dir.getPath() + File.separator + "VID" + timeStamp+ ".3gp");
        }
        else {
            return null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
        } catch (Exception e) {

        }
        Camera.Parameters parameters;
        parameters = camera.getParameters();
        parameters.setPreviewSize(352, 288);
        parameters.setPreviewFrameRate(20);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}