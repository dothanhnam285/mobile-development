package com.ant.myteam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "Song")
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date date_added;

    private float rating;

    @Column(nullable = false)
    private String video_id;

    private String poster;

    @ManyToOne
    @JoinColumn(name = "singer_id")
    private Singer singer;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "song_id")
    private Collection<Record> records;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "song_id")
    private Collection<RatingSong> ratingSongs;
    
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "song_id")
    private HotSong hotSong;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTimeViewed;

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
        ArrayList<RatingSong> ratingList = new ArrayList<RatingSong>(ratingSongs);
        if (ratingList.size() > 0) {
            for (int i = 0; i < ratingList.size(); i++) {
                rating += (float) ratingList.get(i).getRateValue();
            }
            rating = rating / ratingList.size();
        }
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

    /**
     * @return the title_search
     */
    public String getTitle_search() {
        return title_search;
    }

    /**
     * @param title_search the title_search to set
     */
    public void setTitle_search(String title_search) {
        this.title_search = title_search;
    }

    /**
     * @return the records
     */
    public Collection<Record> getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(Collection<Record> records) {
        this.records = records;
    }

    /**
     * @return the ratingSongs
     */
    public Collection<RatingSong> getRatingSongs() {
        return ratingSongs;
    }

    /**
     * @param ratingSongs the ratingSongs to set
     */
    public void setRatingSongs(Collection<RatingSong> ratingSongs) {
        this.ratingSongs = ratingSongs;
    }

    /**
     * @return the hotSong
     */
    public HotSong getHotSong() {
        return hotSong;
    }

    /**
     * @param hotSong the hotSong to set
     */
    public void setHotSong(HotSong hotSong) {
        this.hotSong = hotSong;
    }

    /**
     * @return the lastTimeViewed
     */
    public Date getLastTimeViewed() {
        return lastTimeViewed;
    }

    /**
     * @param lastTimeViewed the lastTimeViewed to set
     */
    public void setLastTimeViewed(Date lastTimeViewed) {
        this.lastTimeViewed = lastTimeViewed;
    }

}
