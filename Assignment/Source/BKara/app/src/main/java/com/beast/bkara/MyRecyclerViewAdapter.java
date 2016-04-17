package com.beast.bkara;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.beast.bkara.model.Record;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter.ItemViewArg;

/**
 * Created by Darka on 4/17/2016.
 */
public class MyRecyclerViewAdapter<T> extends BindingRecyclerViewAdapter<T> {

    private MediaPlayer mediaPlayer;
    private Context mContext;
    private Handler threadHandler;
    private List<ToggleButton> toggleButtonList;
    private List<SeekBar> seekBarList;
    private UpdateSeekBarThread updateSeekBarThread;

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

    public MyRecyclerViewAdapter(@NonNull ItemViewArg<T> arg, Context context) {
        super(arg);
        this.mContext = context;
        toggleButtonList = new ArrayList<ToggleButton>();
        seekBarList = new ArrayList<SeekBar>();
        updateSeekBarThread = new UpdateSeekBarThread();
        threadHandler = new Handler();
    }

    public void StopMediaPlayer() {
        if (mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = null;
        for(int i=0; i < seekBarList.size(); i++) {
            seekBarList.get(i).setProgress(0);
            toggleButtonList.get(i).setChecked(false);
        }
    }

    @Override
    public ViewDataBinding onCreateBinding(LayoutInflater inflater, @LayoutRes int layoutId, ViewGroup viewGroup) {
        ViewDataBinding binding = super.onCreateBinding(inflater, layoutId, viewGroup);
        Log.d("BINDING ADAPTER", "created binding: " + binding);
        return binding;
    }

    @Override
    public void onBindBinding(ViewDataBinding binding, int bindingVariable, @LayoutRes int layoutId, int position, T item) {
        super.onBindBinding(binding, bindingVariable, layoutId, position, item);
        Log.d("BINDING ADAPTER", "bound binding: " + binding + " at position: " + position);
        final Record record = (Record) item;
        View v = binding.getRoot();
        Log.d("MEDIA PLAYER", v.toString());
        ToggleButton toggleButton = (ToggleButton) v.findViewById(R.id.record_item_btnPlay);
        SeekBar seekBar = (SeekBar) v.findViewById(R.id.record_item_seekBar);
        seekBar.setEnabled(false);

        toggleButtonList.add(toggleButton);
        seekBarList.add(seekBar);

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

        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isPlay) {

                int itemIndex = toggleButtonList.indexOf((ToggleButton) compoundButton);
                SeekBar sbar = seekBarList.get(itemIndex);
                int currentProgress = seekBarList.get(itemIndex).getProgress();

                if (isPlay) {
                    sbar.setEnabled(true);
                    for(int i=0; i < seekBarList.size(); i++) {
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
                        Log.d("MEDIA PLAYER", "START");
                        mediaPlayer = MediaPlayer.create(mContext, Uri.parse(record.getDummy_path()));
                        sbar.setMax(mediaPlayer.getDuration());
                        mediaPlayer.setLooping(true);
                        Log.d("MEDIA PLAYER", String.valueOf(mediaPlayer.getDuration()));
                        mediaPlayer.start();
                    }
                    else {
                        mediaPlayer.start();
                    }
                    updateSeekBarThread.sbar = sbar;
                    updateSeekBarThread.isRepeat = true;
                    Log.d("MEDIA PLAYER", "THREAD BEGIN");
                    threadHandler.postDelayed(updateSeekBarThread, 500);
                }
                else {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                }
            }
        });
    }
}
