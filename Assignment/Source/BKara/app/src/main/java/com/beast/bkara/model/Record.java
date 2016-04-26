package com.beast.bkara.model;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Darka on 4/16/2016.
 */
public class Record extends BaseObservable {

    @SerializedName("recordId")
    private int id;

    private int length;
    private int view;
    private Date date_created;
    private float rating;
    private Song song;
    private User user;
    private String stream_link;


    @BindingAdapter("app:setDate")
    public static void setDate(TextView view, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String strDate = sdf.format(date);
        view.setText(strDate);
    }

    @BindingAdapter("app:setView")
    public static void setView(TextView view, int recordView) {
        view.setText(String.valueOf(recordView) + " View(s)");
    }

    @BindingAdapter("app:setUsername")
    public static void setUsername(TextView view, User user) {
        view.setText(user.getUserName());
    }

    @BindingAdapter("app:setSongname")
    public static void setSongname(TextView view, Song song) {
        view.setText(song.getTitle());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getStream_link() {
        return stream_link;
    }

    public void setStream_link(String stream_link) {
        this.stream_link = stream_link;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
