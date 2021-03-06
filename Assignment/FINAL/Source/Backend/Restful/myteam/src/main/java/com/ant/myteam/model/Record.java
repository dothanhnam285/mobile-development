/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ant.myteam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;

/**
 *
 * @author VINH
 */
@Entity
@Table(name = "Record")
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long recordId;
    private Float rating;
    private Long view;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_created;
    private Long length;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private String stream_link;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "recordId")
    private Collection<RatingRecord> ratingRecords;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastTimeViewed;

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Float getRating() {
        ArrayList<RatingRecord> ratingList = new ArrayList<RatingRecord>(getRatingRecords());
        if (ratingList.size() > 0) {
            for (int i = 0; i < ratingList.size(); i++) {
                rating += (float) ratingList.get(i).getRateValue();
            }
            rating = rating / ratingList.size();
        }
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    /**
     * @return the song
     */
    public Song getSong() {
        return song;
    }

    /**
     * @param song the song to set
     */
    public void setSong(Song song) {
        this.song = song;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the stream_link
     */
    public String getStream_link() {
        return stream_link;
    }

    /**
     * @param stream_link the stream_link to set
     */
    public void setStream_link(String stream_link) {
        this.stream_link = stream_link;
    }

    /**
     * @return the ratingRecords
     */
    public Collection<RatingRecord> getRatingRecords() {
        return ratingRecords;
    }

    /**
     * @param ratingRecords the ratingRecords to set
     */
    public void setRatingRecords(Collection<RatingRecord> ratingRecords) {
        this.ratingRecords = ratingRecords;
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
