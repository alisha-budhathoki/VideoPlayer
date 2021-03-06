package com.halfwaiter.videoplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    Button btnRecord, btnCustomCapture, btnCapMyVideo;
    TextView myCamera;
    private final int VIDEO_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myCamera = findViewById(R.id.txtMyCamera);
        btnRecord = findViewById(R.id.btnCaptureVideo);
        btnCustomCapture = findViewById(R.id.btnCaptureCustomVideo);
        btnCapMyVideo = findViewById(R.id.capMyVideo);
        videoView = findViewById(R.id.myvideoview);
        String videoPath = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.nextt_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();
        System.out.println("dnjdn");
        btnCapMyVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent goIntent = new Intent(MainActivity.this, CaptureMyVideo.class);
            startActivity(goIntent);
            }
        });
        btnCustomCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hvhhhv");
                Intent intent = new Intent(MainActivity.this, CaptureVideo.class);
                startActivity(intent);
            }
        });
        myCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hvhhhv");
                Intent intent = new Intent(MainActivity.this, MyCamera.class);
                startActivity(intent);
            }
        });
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureVideo(v);
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying())
                    videoView.pause();
                else
                    videoView.start();
            }
        });
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }

    public void captureVideo(View view) {

        System.out.println("nsdsj");
        Intent camera_intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        For opening camera
//Passing file path for saving file
        File video_file = getFielPath();
//        Now converting this file to uri
        Uri video_uri = Uri.fromFile(video_file);
//        Now adding this file path into the intent object
        camera_intent.putExtra(MediaStore.ACTION_VIDEO_CAPTURE, video_uri);
//        specifying quality for video
        camera_intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); //1 specifies the highest quality
        startActivityForResult(camera_intent, VIDEO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("hdshudsh");
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("dsnkjjsdb" + requestCode);
        if (requestCode == VIDEO_REQUEST_CODE) {
            System.out.println("dnsjnsj" + resultCode);
            if (resultCode == RESULT_OK) {
                //            Successfuly capture the video
                Toast.makeText(getApplicationContext(), "Video successfully Recorded", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Video capture failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    public File getFielPath() {
        System.out.println("skjsddf");
        File folder = new File(getApplicationContext().getExternalFilesDir(null), "jasa");
//        File folder = new File(getApplicationContext().getExternalFilesDir(null),"myfile.mp4");        System.out.println("njsjjds"+ this.getFilesDir().getPath());
//        for saving app file
        if (folder.exists()) {
            folder.mkdir();
        }
//        For saving video file
        File video_file = new File("sample_video.mp4");
        return video_file;
    }

    public void auioPlay(View view) throws IOException {
        Intent intent = new Intent(this, AudioActivity.class);
        startActivity(intent);
    }

    public void exoAudioPlay(View view) {
        Intent intent = new Intent(this, ExoAudioActivity.class);
        startActivity(intent);
    }
//        String url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3"; // your URL here
//        MediaPlayer mPlayer = new MediaPlayer();
//        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        if (((ToggleButton) view).isChecked()) {
//
////        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.my_mp3);
////        mPlayer.start();
//
//
////        mPlayer.setDataSource(url);
////        mPlayer.prepare(); // might take long! (for buffering, etc)
////        mPlayer.start();
//            try {
//                System.out.println("mnkjsjs");
//                mPlayer.setDataSource(url);
//                mPlayer.prepareAsync(); // prepare async to not block main thread
//            } catch (IOException e) {
//                Toast.makeText(this, "mp3 not found", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                @Override
//                public void onPrepared(MediaPlayer player) {
//                    player.start();
//                    System.out.println("mnsdds");
//                }
//
//            });
//        }
//        else{
//            System.out.println("jnsdjdsn");
//            mPlayer.stop();
//        }
//    }
}