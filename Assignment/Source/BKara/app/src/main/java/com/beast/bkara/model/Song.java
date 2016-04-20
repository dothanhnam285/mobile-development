package com.beast.bkara.model;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;

/**
 * Created by Darka on 4/9/2016.
 */
public class Song extends BaseObservable implements Parcelable {

    private int song_id;
    private String title;
    private int view;
    private String poster;
    private Date date_added;
    private float rating;
    private String genre;
    private Singer singer;
    private String video_id;

    @BindingAdapter("app:setGenre")
    public static void setGenre(TextView view, String songGenre) {
        String text = "<b>Genre: </b>" + songGenre;
        view.setText(Html.fromHtml(text));
    }

    @BindingAdapter("app:setSinger")
    public static void setArtist(TextView view, Singer singer) {
        String text = "";
        if (singer != null && singer.getName() != null && singer.getName() != "")
            text = "<b>Singer: </b>" + singer.getName();
        else
            text = "<b>Singer: </b> Unknown";
        view.setText(Html.fromHtml(text));
    }

/*    @BindingAdapter("app:setSinger")
    public static void setArtist(TextView view, String singer) {
        String text = "";
        if (singer != null && singer != null)
            text = "<b>Singer: </b>" + singer;
        else
            text = "<b>Singer: </b> Unknown";
        view.setText(Html.fromHtml(text));
    }*/

    @BindingAdapter("app:setPoster")
    public static void setPoster(ImageView view, String songPoster) {
        ImageLoader.getInstance().displayImage(songPoster, view);
    }


    @BindingAdapter("app:setView")
    public static void setView(TextView view, int songView) {
        view.setText(String.valueOf(songView) + " View(s)");
    }

    public Song() {

    }


    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
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

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    //Parcelling part
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getSong_id());
        parcel.writeString(getTitle());
        parcel.writeInt(getView());
        parcel.writeValue(getDate_added());
        parcel.writeFloat(getRating());
        parcel.writeString(getGenre());
        parcel.writeString(getPoster());
        parcel.writeParcelable(getSinger(), i);
        parcel.writeString(getVideo_id());
    }

    private Song(Parcel in) {
        setSong_id(in.readInt());
        setTitle(in.readString());
        setView(in.readInt());
        setDate_added((Date) in.readValue(null));
        setRating(in.readFloat());
        setGenre(in.readString());
        setPoster(in.readString());
        setSinger((Singer) in.readParcelable(Singer.class.getClassLoader()));
        setVideo_id(in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Song> CREATOR
            = new Parcelable.Creator<Song>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

}
