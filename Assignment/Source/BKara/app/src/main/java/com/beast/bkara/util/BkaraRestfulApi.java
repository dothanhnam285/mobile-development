package com.beast.bkara.util;

import com.beast.bkara.model.Song;
import com.beast.bkara.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;

/**
 * Created by Darka on 4/18/2016.
 */

// Interface for calling restful server
public interface BkaraRestfulAPI {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("songlist/all")
    Call<List<Song>> getSongListAll();

    @GET("songlist/search/name/{songname}")
    Call<List<Song>> findSongsByName(@Path("songname") String songName);

    @POST("signUp")
    Call<ResponseBody> signUp(@Body User user);

    @POST("login")
    Call<ResponseBody> login(@Body User user);
}
