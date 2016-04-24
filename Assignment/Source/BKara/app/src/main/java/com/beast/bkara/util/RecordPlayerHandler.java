package com.beast.bkara.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darka on 4/24/2016.
 */
public class RecordPlayerHandler {

    private MediaPlayer mediaPlayer;
    private Context mContext;
    private Handler threadHandler;
    private List<ToggleButton> toggleButtonList;
    private List<SeekBar> seekBarList;
    private List<String> pathList;
    private UpdateSeekBarThread updateSeekBarThread;
    private boolean isRemote;

    private class UpdateSeekBarThread implements Runnable {
        public SeekBar sbar;
        public boolean isRepeat = false;

        public void run() {
            if (mediaPlayer != null) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                sbar.setProgress(currentPosition);
                if (isRepeat)
                    threadHandler.postDelayed(this, 500);
            }
        }
    }

    public RecordPlayerHandler(Context context, boolean isRemote) {
        this.mContext = context;
        this.isRemote = isRemote;
        toggleButtonList = new ArrayList<ToggleButton>();
        seekBarList = new ArrayList<SeekBar>();
        pathList = new ArrayList<String>();
        updateSeekBarThread = new UpdateSeekBarThread();
        threadHandler = new Handler();
    }

    public void AddRecordInfo(final ToggleButton toggleButton, final SeekBar seekBar, final ProgressBar progressBar, String path) {

        seekBar.setEnabled(false);

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    SeekBar sbar = (SeekBar) view;
                    int currentProgress = sbar.getProgress();
                    if (currentProgress != 0) {
                        mediaPlayer.seekTo(currentProgress);
                    }
                }
                return false;
            }

            ;

        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isPlay) {

                int itemIndex = toggleButtonList.indexOf((ToggleButton) compoundButton);
                final SeekBar sbar = seekBarList.get(itemIndex);
                int currentProgress = seekBarList.get(itemIndex).getProgress();

                if (isPlay) {
                    sbar.setEnabled(true);
                    for (int i = 0; i < seekBarList.size(); i++) {
                        if (i != itemIndex) {
                            seekBarList.get(i).setProgress(0);
                            seekBarList.get(i).setEnabled(false);
                            toggleButtonList.get(i).setChecked(false);
                        }
                    }
                    if (currentProgress == 0) {
                        if (mediaPlayer != null) {
                            updateSeekBarThread.isRepeat = false;
                            mediaPlayer.release();
                        }

                        if (isRemote) {

                            toggleButton.setVisibility(View.INVISIBLE);
                            seekBar.setVisibility(View.INVISIBLE);

                            if (progressBar != null)
                                progressBar.setVisibility(View.VISIBLE);

                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            try {
                                mediaPlayer.setDataSource("https://api.soundcloud.com/tracks/260635969/stream?client_id=cab81827c76e36c852118774d8eef584");
                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        toggleButton.setVisibility(View.VISIBLE);
                                        seekBar.setVisibility(View.VISIBLE);

                                        if (progressBar != null)
                                            progressBar.setVisibility(View.GONE);

                                        sbar.setMax(mediaPlayer.getDuration());
                                        mediaPlayer.setLooping(true);
                                        mediaPlayer.start();
                                    }
                                });
                                mediaPlayer.prepareAsync();


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            mediaPlayer = MediaPlayer.create(mContext, Uri.parse(pathList.get(itemIndex)));
                            sbar.setMax(mediaPlayer.getDuration());
                            mediaPlayer.setLooping(true);
                            mediaPlayer.start();
                        }
                    } else {
                        mediaPlayer.start();
                    }
                    updateSeekBarThread.sbar = sbar;
                    updateSeekBarThread.isRepeat = true;
                    Log.d("MEDIA PLAYER", "THREAD BEGIN");
                    threadHandler.postDelayed(updateSeekBarThread, 500);
                } else {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                }
            }
        });

        toggleButtonList.add(toggleButton);
        seekBarList.add(seekBar);
        pathList.add(path);
    }

    public void StopMediaPlayer() {
        if (mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = null;
        for (int i = 0; i < seekBarList.size(); i++) {
            seekBarList.get(i).setProgress(0);
            toggleButtonList.get(i).setChecked(false);
        }
    }

}
