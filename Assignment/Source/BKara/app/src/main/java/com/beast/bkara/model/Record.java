package com.beast.bkara.model;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Darka on 4/16/2016.
 */
public class Record extends BaseObservable {

    private int id;
    private int length;
    private int view;
    private Date date_created;
    private float rating;
    private String dummy_path;


    @BindingAdapter("app:setDate")
    public static void setDate(TextView view, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = sdf.format(date);
        view.setText(strDate);
    }

    @BindingAdapter("app:setView")
    public static void setView(TextView view, int recordView) {
        view.setText(String.valueOf(recordView) + " View(s)");
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

    public String getDummy_path() {
        return dummy_path;
    }

    public void setDummy_path(String dummy_path) {
        this.dummy_path = dummy_path;
    }
}
