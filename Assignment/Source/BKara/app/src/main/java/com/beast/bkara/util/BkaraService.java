package com.beast.bkara.util;

import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.beast.bkara.model.Song;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Darka on 4/22/2016.
 */
public class BkaraService {

    private static BkaraService ourInstance = new BkaraService();

    public static BkaraService getInstance() {
        return ourInstance;
    }

    private final String RESTFUL_URL = "http://192.168.1.103:8084/myteam/bkaraservice/";
    private BkaraRestfulAPI bkaraRestful;

    public enum WhichList {
        ALL, HOT, NEW, SEARCH
    }

    public enum SearchFilter {
        SONG_NAME, SINGER_NAME
    }

    private void SetupRestfulService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RESTFUL_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        bkaraRestful = retrofit.create(BkaraRestfulAPI.class);
    }

    public void GetSongList(final WhichList whichList, final ObservableList<Song> songList, final ProgressBar progressBar) {
        Call<List<Song>> call;

        switch(whichList) {
            case HOT:
                call = bkaraRestful.getSongListAll();
                break;
            case NEW:
                call = bkaraRestful.getSongListAll();
                break;
            default:
                call = bkaraRestful.getSongListAll();
                break;
        }

        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                Log.d("RESTFUL CALL", "SUCCESS");
                songList.clear();
                songList.addAll(response.body());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d("RESTFUL CALL", "FAILED " + t.getMessage());
                GetSongList(whichList, songList, progressBar);
            }
        });
    }

    public void FindSongs(final SearchFilter searchFilter, final String searchValue, final ObservableList<Song> songList, final ProgressBar progressBar) {
        Call<List<Song>> call;

        switch(searchFilter) {
            case SINGER_NAME:
                call = bkaraRestful.findSongsBySingerName(searchValue);
                break;
            default:
                call = bkaraRestful.findSongsByName(searchValue);
                break;
        }

        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                Log.d("RESTFUL CALL", "SUCCESS " + response.toString());
                songList.clear();
                songList.addAll(response.body());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d("RESTFUL CALL", "FAILURE " + t.getMessage());
                FindSongs(searchFilter, searchValue, songList, progressBar);
            }
        });
    }

    private BkaraService() {
        SetupRestfulService();
    }
}
