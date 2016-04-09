package com.beast.bkara.model;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by Darka on 4/9/2016.
 */
public class Song extends BaseObservable {

    private int id;
    private String title;
    private int view;
    private String poster;
    private int length;
    private Date date_added;
    private float rating;
    private String genre;
    private String singer;

    @BindingAdapter("app:setGenre")
    public static void setGenre(TextView view, String songGenre) {
        String text = "<b>Genre: </b>" + songGenre;
        view.setText(Html.fromHtml(text));
    }

    @BindingAdapter("app:setArtist")
    public static void setArtist(TextView view, String songSinger) {
        String text = "<b>Artist: </b>" + songSinger;
        view.setText(Html.fromHtml(text));
    }


    @BindingAdapter("app:setView")
    public static void setView(TextView view, int songView) {
        view.setText(String.valueOf(songView) + " View(s)");
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
