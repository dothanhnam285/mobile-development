package com.beast.bkara.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Environment;

import com.beast.bkara.BR;
import com.beast.bkara.R;
import com.beast.bkara.model.Record;

import java.util.Date;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by Darka on 4/16/2016.
 */
public class RecordViewModel {

    public ObservableList<Record> recordList;
    public final ItemView recordView = ItemView.of(BR.record, R.layout.record_item);

    public RecordViewModel() {
        InitDummyData();
    }

    private void InitDummyData() {
        recordList = new ObservableArrayList<>();
        for(int i=0; i<5; i++) {
            Record rec = new Record();
            rec.setView(6969);
            rec.setDate_created(new Date());
            if (i % 2 == 0)
                rec.setDummy_path(Environment.getExternalStorageDirectory().getPath() + "/test.m4a");
            else
                rec.setDummy_path(Environment.getExternalStorageDirectory().getPath() + "/hangouts_incoming_call.ogg");
            recordList.add(rec);
        }
    }

}
