package com.beast.bkara.util;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.beast.bkara.R;
import com.beast.bkara.model.Record;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter.ItemViewArg;

/**
 * Created by Darka on 4/17/2016.
 */
public class RecordListRecyclerViewAdapter<T> extends BindingRecyclerViewAdapter<T> {

    private RecordPlayerHandler recordPlayerHandler;

    public RecordListRecyclerViewAdapter(@NonNull ItemViewArg<T> arg, Context context) {
        super(arg);
        recordPlayerHandler = new RecordPlayerHandler(context, true);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recordPlayerHandler.StopMediaPlayer();
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

        ToggleButton toggleButton = (ToggleButton) v.findViewById(R.id.record_item_btnPlay);
        SeekBar seekBar = (SeekBar) v.findViewById(R.id.record_item_seekBar);
        ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.record_item_progressbar);

        recordPlayerHandler.AddRecordInfo(toggleButton, seekBar, progressBar, record.getDummy_path());
    }
}
