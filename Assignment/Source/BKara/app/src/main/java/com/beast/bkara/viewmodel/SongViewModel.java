package com.beast.bkara.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.beast.bkara.BR;
import com.beast.bkara.Controller;
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

    public ObservableList<Song> songListAll;
    public ObservableList<Song> songListHot;
    public ObservableList<Song> songListNew;
    public final ItemView songView = ItemView.of(BR.song, R.layout.song_item);

    private Controller controller;

    public SongViewModel(Controller controller) {
        this.controller = controller;
        InitDummyData();
    }

    private void InitDummyData() {
        songListAll = new ObservableArrayList<>();
        controller.GetSongListAll(songListAll);
/*        for(int i=0; i<5; i++) {
            Song song = new Song();
            song.setTitle("Chac ai do se ve");
            song.setGenre("Nhac tre");
            song.setSinger("Son Tung");
            song.setView(2048);
            song.setVideo_id("vtxn0i4CVX8");
            songListAll.add(song);
        }*/

        songListHot = new ObservableArrayList<>();
        for(int i=0; i<5; i++) {
            Song song = new Song();
            song.setTitle("Sau tat ca");
            song.setGenre("Nhac tre");
            song.setSinger("Erik");
            song.setView(2048);
            song.setVideo_id("eEZpywMUzGI");
            songListHot.add(song);
        }

        songListNew = new ObservableArrayList<>();
        for(int i=0; i<5; i++) {
            Song song = new Song();
            song.setTitle("Trai tim ben le");
            song.setGenre("Nhac vang");
            song.setSinger("Bang Kieu");
            song.setView(2048);
            song.setVideo_id("u3i3bIzt2xg");
            songListNew.add(song);
        }
    }

}
