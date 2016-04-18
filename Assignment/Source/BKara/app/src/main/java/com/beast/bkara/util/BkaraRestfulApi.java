package com.beast.bkara.util;

import com.beast.bkara.model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Darka on 4/18/2016.
 */

// Interface for calling restful server
public interface BkaraRestfulApi {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("songlist/all")
    Call<List<Song>> getSongListAll();
}
