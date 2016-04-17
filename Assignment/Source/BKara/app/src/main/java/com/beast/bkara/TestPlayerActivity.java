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
    private ToggleButton btnTestPlay2;
    private ToggleButton btnTestPlay3;
    private SeekBar seekBarTest;
    private SeekBar seekBarTest2;
    private SeekBar seekBarTest3;

    private Handler threadHandler = new Handler();

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_player);

        final UpdateSeekBarThread updateSeekBarThread = new UpdateSeekBarThread();

        btnTestPlay = (ToggleButton) findViewById(R.id.btnTestPlay);
        seekBarTest = (SeekBar) findViewById(R.id.seekBarTest);
        seekBarTest.setEnabled(false);

        btnTestPlay2 = (ToggleButton) findViewById(R.id.btnTestPlay2);
        seekBarTest2 = (SeekBar) findViewById(R.id.seekBarTest2);
        seekBarTest2.setEnabled(false);

        btnTestPlay3 = (ToggleButton) findViewById(R.id.btnTestPlay3);
        seekBarTest3 = (SeekBar) findViewById(R.id.seekBarTest3);
        seekBarTest3.setEnabled(false);

        final String path = Environment.getExternalStorageDirectory() + "/";
        Log.d("MP PATH", path);
        String filename = "haha";
        //mediaPlayer = MediaPlayer.create(this, Uri.parse(path + filename + ".m4a"));
        //seekBarTest.setMax(mediaPlayer.getDuration());

        btnTestPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isPlay) {
                int currentProgress = seekBarTest.getProgress();

                if (isPlay) {
                    Log.d("MP", "isPlay = true");
                    seekBarTest2.setProgress(0);
                    seekBarTest3.setProgress(0);
                    seekBarTest.setEnabled(true);
                    seekBarTest2.setEnabled(false);
                    seekBarTest3.setEnabled(false);
                    btnTestPlay2.setChecked(false);
                    btnTestPlay3.setChecked(false);
                    if (currentProgress == 0) {
                        if (mediaPlayer != null) {
                            updateSeekBarThread.isRepeat = false;
                            mediaPlayer.release();
                        }
                        mediaPlayer = MediaPlayer.create(TestPlayerActivity.this, Uri.parse(path + "haha.m4a"));
                        mediaPlayer.setLooping(true);
                        seekBarTest.setMax(mediaPlayer.getDuration());
                        mediaPlayer.start();
                    }
                    else {
                        Log.d("MP", "START SEEK");
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                        mediaPlayer.start();
                    }
                    updateSeekBarThread.sbar = seekBarTest;
                    updateSeekBarThread.isRepeat = true;
                    threadHandler.postDelayed(updateSeekBarThread, 1000);
                }
                else {
                    Log.d("MP", "isPlay = false");
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        Log.d("MP", "PAUSE");
                        mediaPlayer.pause();
                    }
                }
            }
        });

        btnTestPlay2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isPlay) {
                int currentProgress = seekBarTest2.getProgress();

                if (isPlay) {
                    Log.d("MP2", "isPlay = true");
                    seekBarTest.setProgress(0);
                    seekBarTest3.setProgress(0);
                    seekBarTest2.setEnabled(true);
                    seekBarTest.setEnabled(false);
                    seekBarTest3.setEnabled(false);
                    btnTestPlay.setChecked(false);
                    btnTestPlay3.setChecked(false);
                    if (currentProgress == 0) {
                        if (mediaPlayer != null) {
                            updateSeekBarThread.isRepeat = false;
                            mediaPlayer.release();
                        }
                        Log.d("MP2", "START PLAY");
                        mediaPlayer = MediaPlayer.create(TestPlayerActivity.this, Uri.parse(path + "Voice2.3gpp"));
                        mediaPlayer.setLooping(true);
                        seekBarTest2.setMax(mediaPlayer.getDuration());
                        mediaPlayer.start();
                    }
                    else {
                        Log.d("MP2", "START SEEK");
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                        mediaPlayer.start();
                    }

                    updateSeekBarThread.sbar = seekBarTest2;
                    updateSeekBarThread.isRepeat = true;
                    threadHandler.postDelayed(updateSeekBarThread, 1000);

                }
                else {
                    Log.d("MP2", "isPlay = false");
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        Log.d("MP2", "PAUSE");
                        mediaPlayer.pause();
                    }
                }
            }
        });

        btnTestPlay3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isPlay) {

                int currentProgress = seekBarTest3.getProgress();

                if (isPlay) {
                    Log.d("MP3", "isPlay = true");
                    seekBarTest.setProgress(0);
                    seekBarTest2.setProgress(0);
                    seekBarTest3.setEnabled(true);
                    seekBarTest.setEnabled(false);
                    seekBarTest2.setEnabled(false);
                    btnTestPlay.setChecked(false);
                    btnTestPlay2.setChecked(false);
                    if (currentProgress == 0) {
                        if (mediaPlayer != null) {
                            updateSeekBarThread.isRepeat = false;
                            mediaPlayer.release();
                        }
                        mediaPlayer = MediaPlayer.create(TestPlayerActivity.this, Uri.parse(path + "hangouts_incoming_call.ogg"));
                        mediaPlayer.setLooping(true);
                        seekBarTest3.setMax(mediaPlayer.getDuration());
                        mediaPlayer.start();
                    }
                    else {
                        Log.d("MP3", "START SEEK");
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                        mediaPlayer.start();
                    }

                    updateSeekBarThread.sbar = seekBarTest3;
                    updateSeekBarThread.isRepeat = true;
                    threadHandler.postDelayed(updateSeekBarThread, 1000);

                }
                else {
                    Log.d("MP3", "isPlay = false");
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        Log.d("MP3", "PAUSE");
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
                if (currentProgress != 0)
                    mediaPlayer.seekTo(currentProgress);
                return false;
            }
        });

        seekBarTest2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar sbar = (SeekBar) view;
                int currentProgress = sbar.getProgress();
                if (currentProgress != 0)
                    mediaPlayer.seekTo(currentProgress);
                return false;
            }
        });

        seekBarTest3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar sbar = (SeekBar) view;
                int currentProgress = sbar.getProgress();
                if (currentProgress != 0)
                    mediaPlayer.seekTo(currentProgress);
                return false;
            }
        });
    }

    private class UpdateSeekBarThread implements Runnable {
        public SeekBar sbar;
        public boolean isRepeat = false;
        public void run() {
            if (mediaPlayer != null) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                sbar.setProgress(currentPosition);
                if (isRepeat)
                    threadHandler.postDelayed(this, 1000);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = null;
        seekBarTest.setProgress(0);
        seekBarTest2.setProgress(0);
        seekBarTest3.setProgress(0);
        btnTestPlay.setChecked(false);
        btnTestPlay2.setChecked(false);
        btnTestPlay3.setChecked(false);

    }
}
