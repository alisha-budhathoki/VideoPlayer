package com.halfwaiter.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AudioActivity extends AppCompatActivity {
    ImageView impPlayPause;
    private TextView textCurrentTime, txtTotalDuration;
    private SeekBar playerSeekbar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        impPlayPause = findViewById(R.id.img_play_btn);
        textCurrentTime = findViewById(R.id.txt_current_time);
        txtTotalDuration = findViewById(R.id.txt_total_duration);
        playerSeekbar = findViewById(R.id.play_seekbar);
        mediaPlayer = new MediaPlayer();
        playerSeekbar.setMax(100);

        impPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    impPlayPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                } else {
                    mediaPlayer.start();
                    impPlayPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    updateSeekBar();
                }
            }
        });
        prepareMediaPlayer();
        playerSeekbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar = (SeekBar) v;
                int playPosition = (mediaPlayer.getDuration() /100)*seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                textCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playerSeekbar.setSecondaryProgress(percent);
            }
        });
    }

    private void prepareMediaPlayer(){
        try {
            mediaPlayer.setDataSource("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3");
            mediaPlayer.prepare();
            txtTotalDuration.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
        }catch (Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            textCurrentTime.setText(milliSecondsToTimer(currentDuration));
        }
    };

    private void updateSeekBar() {
        if (mediaPlayer.isPlaying()) {
            playerSeekbar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    private String milliSecondsToTimer(long milliseconds) {
        String timerString = "";
        String secondsString;
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            timerString = hours + ":";
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        timerString = timerString + minutes + ":" + secondsString;
        return timerString;
    }
}