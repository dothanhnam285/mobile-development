package com.beast.bkara;

import android.app.Application;
import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.beast.bkara.model.Song;
import com.beast.bkara.model.User;
import com.beast.bkara.util.BkaraRestfulAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Darka on 4/7/2016.
 */
public class Controller extends Application {

    public static final String YOUTUBE_API_KEY = "AIzaSyDyyVofQ_tgdvQEh30ikZ7LipiQbWeLA1g";
    public static final String RESTFUL_URL = "http://192.168.1.103:8084/myteam/bkaraservice/";
            //"http://192.168.1.102:8080/myteam/bkaraservice/";



    private boolean _loginStatus = false;
    private BkaraRestfulAPI bkaraService;


    private void SetupRestfulService() {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd-MM-yyyy")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RESTFUL_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        bkaraService = retrofit.create(BkaraRestfulAPI.class);
    }


    public void login(User user, Callback<ResponseBody> cb){
        bkaraService.login(user).enqueue(cb);
    }

    public void signUp(User user, Callback<ResponseBody> cb) {
        bkaraService.signUp(user).enqueue(cb);
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

/*    public void Check() {
        Call<ResponseBody> call = bkaraService.check("ph");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("CHECK", "SUCCESS " + response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("CHECK", "FAILED " + t.getMessage());
            }
        });
    }*/

    public Controller() {
        SetupRestfulService();
        //Check();
    }

    public boolean isLogin() {
        return _loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this._loginStatus = _loginStatus;
    }
}
