package com.ant.myteam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name ="Song")
public class Song implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long song_id;

    @Column(nullable = false)
    private String title;
    
    @Column(nullable = true)
    private String title_search;

    private int view;
    private Date date_added;
    private float rating;

    @Column(nullable = false)
    private String video_id;

    private String poster;

    @ManyToOne
    @JoinColumn(name = "singer_id")
    private Singer singer;

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

    public String getVideo_id() {
            return video_id;
    }

    public void setVideo_id(String video_id) {
            this.video_id = video_id;
    }

    public String getTitle() {
            return title;
    }

    public void setTitle(String title) {
            this.title = title;
    }

    /**
     * @return the poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     * @param poster the poster to set
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     * @return the song_id
     */
    public Long getSong_id() {
        return song_id;
    }

    /**
     * @param song_id the song_id to set
     */
    public void setSong_id(Long song_id) {
        this.song_id = song_id;
    }

    /**
     * @return the singer
     */
    public Singer getSinger() {
        return singer;
    }

    /**
     * @param singer the singer to set
     */
    public void setSinger(Singer singer) {
        this.singer = singer;
    }

}
