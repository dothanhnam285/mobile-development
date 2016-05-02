package com.beast.bkara.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;
import android.widget.ProgressBar;

import com.beast.bkara.BR;
import com.beast.bkara.R;
import com.beast.bkara.model.Record;
import com.beast.bkara.model.Song;
import com.beast.bkara.model.User;
import com.beast.bkara.util.bkararestful.BkaraService;
import com.beast.bkara.util.UploadToSoundCloudTask;

import java.util.ArrayList;
import java.util.Date;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by Darka on 4/16/2016.
 */
public class RecordViewModel {

    public ObservableList<Record> recordList;
    public final ItemView recordView = ItemView.of(BR.record, R.layout.record_item);

    private User user;
    private Song song;

    private BkaraService bkaraService;


    public RecordViewModel(ArrayList<Record> lstRecordsHistory) {
        recordList = new ObservableArrayList<>();
        recordList.addAll(lstRecordsHistory);
    }

    public RecordViewModel(User user, Song song, ProgressBar progressBar) {
        recordList = new ObservableArrayList<>();
        bkaraService = BkaraService.getInstance();
        this.user = user;
        this.song = song;

        BkaraService.RecordSearchFilter searchFilter;
        Long searchValue;
        if (song != null) {
            searchFilter = BkaraService.RecordSearchFilter.SONG_ID;
            searchValue = song.getSong_id();
        }
        else {
            searchFilter = BkaraService.RecordSearchFilter.USER_ID;
            searchValue = user.getUserId();
        }

        bkaraService.FindRecords(searchFilter, searchValue, recordList, progressBar);
        
    }

    public void SetUser(User user) {
        Log.d("RECORD VM", "SET USER: " + user.toString());
        this.user = user;
    }

    public void SaveRecord(String stream_link) {
        Log.d("RECORD VM", "SAVE RECORD: " + user.toString());
        Record record = new Record();
        record.setDate_created(new Date());
        record.setSong(song);
        record.setUser(user);
        record.setStream_link(stream_link + "?client_id=" + UploadToSoundCloudTask.CLIENT_ID);
        bkaraService.SaveRecord(record);
    }
}
