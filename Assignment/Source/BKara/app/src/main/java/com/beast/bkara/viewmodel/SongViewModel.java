package com.beast.bkara.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.beast.bkara.R;
import com.beast.bkara.model.Song;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by Darka on 4/9/2016.
 */
public class SongViewModel {

    public ObservableList<Song> songList;
    public final ItemView songView = ItemView.of(1, R.layout.song_item);

    public SongViewModel() {

        InitDummyData();

    }

    private void InitDummyData() {
        songList = new ObservableArrayList<>();
        for(int i=0; i<5; i++) {
            Song song = new Song();
            song.setTitle("Chac ai do se ve");
            song.setGenre("Nhac tre");
            song.setSinger("Son Tung");
            song.setView(2048);
            songList.add(song);
        }
    }

}
