package com.beast.bkara;

import android.app.Application;
import android.databinding.ObservableList;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.beast.bkara.model.Song;
import com.beast.bkara.util.BkaraRestfulApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Darka on 4/7/2016.
 */
public class Controller extends Application {

    public static final String YOUTUBE_API_KEY = "AIzaSyDyyVofQ_tgdvQEh30ikZ7LipiQbWeLA1g";
    public static final String RESTFUL_URL = "http://192.168.1.102:8080/myteam/bkaraservice/";

    private boolean _loginStatus = false;
    private BkaraRestfulApi bkaraService;

    private void SetupRestfulService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RESTFUL_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        bkaraService = retrofit.create(BkaraRestfulApi.class);
    }

    public void GetSongListAll(final ObservableList<Song> songListAll, final ProgressBar progressBar) {
        Call<List<Song>> call = bkaraService.getSongListAll();
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                Log.d("RESTFUL SONG LIST ALL", "SUCCESS " + response.body().toString());
                songListAll.clear();
                songListAll.addAll(response.body());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d("RESTFUL SONG LIST ALL", "FAILED " + t.getMessage());
                GetSongListAll(songListAll, progressBar);
            }
        });
    }

    public Controller() {
        SetupRestfulService();
    }

    public boolean isLogin() {
        return _loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this._loginStatus = _loginStatus;
    }
}
