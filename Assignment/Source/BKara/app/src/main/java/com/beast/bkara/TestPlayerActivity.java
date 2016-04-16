package com.beast.bkara;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import java.io.IOException;

public class TestPlayerActivity extends AppCompatActivity {

    private ToggleButton btnTestPlay;
    private SeekBar seekBarTest;
    private Handler threadHandler = new Handler();

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_player);

        btnTestPlay = (ToggleButton) findViewById(R.id.btnTestPlay);
        seekBarTest = (SeekBar) findViewById(R.id.seekBarTest);

        String path = Environment.getExternalStorageDirectory() + "/";
        Log.d("MP", path);
        String filename = "haha";
        mediaPlayer = MediaPlayer.create(this, Uri.parse(path + filename + ".m4a"));
        seekBarTest.setMax(mediaPlayer.getDuration());

        btnTestPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isPlay) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                if (isPlay) {
                    Log.d("MP", "isPlay = true");
                    if (currentPosition == 0) {
                        Log.d("MP", "START");
                        mediaPlayer.start();
                    }
                    else {
                        Log.d("MP", "START SEEK");
                        mediaPlayer.seekTo(currentPosition);
                        mediaPlayer.start();
                    }

                }
                else {
                    Log.d("MP", "isPlay = false");
                    if (mediaPlayer.isPlaying()) {
                        Log.d("MP", "PAUSE");
                        mediaPlayer.pause();
                    }
                }
            }
        });

        seekBarTest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar sbar = (SeekBar) view;
                int currentProgress = sbar.getProgress();
                mediaPlayer.seekTo(currentProgress);
                return false;
            }
        });

        UpdateSeekBarThread updateSeekBarThread = new UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread, 500);
    }

    private class UpdateSeekBarThread implements Runnable {
        public void run() {
            int currentPosition = mediaPlayer.getCurrentPosition();
            seekBarTest.setProgress(currentPosition);
            threadHandler.postDelayed(this, 500);
        }
    }
}
