package com.beast.bkara.util;

import android.databinding.ObservableList;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.beast.bkara.model.Record;
import com.beast.bkara.model.Song;
import com.beast.bkara.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;
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
            //"http://192.168.0.101:8080/myteam/bkaraservice/";
    private BkaraRestfulApi bkaraRestful;

    public enum WhichList {
        ALL, HOT, NEW, SEARCH
    }

    public enum SongSearchFilter {
        SONG_NAME, SINGER_NAME
    }

    public enum RecordSearchFilter {
        SONG_ID, USER_ID
    }

    private void SetupRestfulService() {
        // Restful server return error if keep using dd-MM-yyyy
        // Can not parse date "24-04-2016:00:59:319": not compatible with any of standard forms
        // ("yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "EEE, dd MMM yyyy HH:mm:ss zzz", "yyyy-MM-dd"))
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonDateDeserializer())
                .setDateFormat(/*"dd-MM-yyyy:HH:mm:SS"*/"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RESTFUL_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        bkaraRestful = retrofit.create(BkaraRestfulApi.class);
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

    public void FindSongs(final SongSearchFilter searchFilter, final String searchValue, final ObservableList<Song> songList, final ProgressBar progressBar) {
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

    public void SaveRecord(Record record) {
        Call<Record> call = bkaraRestful.saveRecord(record);
        call.enqueue(new Callback<Record>() {
            @Override
            public void onResponse(Call<Record> call, Response<Record> response) {
                Log.d("RESTFUL CALL", "SUCCESS SAVE RECORD " + response.body());
            }

            @Override
            public void onFailure(Call<Record> call, Throwable t) {
                Log.d("RESTFUL CALL", "FAIL SAVE RECORD " + t.getMessage());
            }
        });
    }

    public void FindRecords(final RecordSearchFilter searchFilter, final Long searchValue, final ObservableList<Record> recordList, final ProgressBar progressBar) {
        Call<List<Record>> call;

        switch (searchFilter) {
            case SONG_ID:
                call = bkaraRestful.findRecordsBySongId(searchValue);
                break;
            default:
                call = bkaraRestful.findRecordsByUserId(searchValue);
                break;
        }

        call.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                Log.d("RESTFUL CALL", "SUCCESS");
                recordList.clear();
                recordList.addAll(response.body());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {
                Log.d("RESTFUL CALL", "FAILED " + t.getMessage());
                FindRecords(searchFilter, searchValue, recordList, progressBar);
            }
        });
    }

    public void login(User user, Callback<User> cb){
        bkaraRestful.login(user).enqueue(cb);
    }

    public void signUp(User user, Callback<User> cb) {
        bkaraRestful.signUp(user).enqueue(cb);
    }

    private BkaraService() {
        SetupRestfulService();
    }

    public class JsonDateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String s = json.getAsJsonPrimitive().getAsString();
            Date d = new Date(Long.parseLong(s));
            return d;
        }
    }
}
