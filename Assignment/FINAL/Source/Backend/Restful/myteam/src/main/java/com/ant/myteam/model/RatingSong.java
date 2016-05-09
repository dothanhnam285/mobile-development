/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ant.myteam.model;

import com.ant.myteam.model.Song;
import com.ant.myteam.model.User;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Darka
 */
@Entity
@Table(name = "RatingSong")
public class RatingSong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private int rateValue;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the song
     */
    public Song getSong() {
        return song;
    }

    /**
     * @param song the song_id to set
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
     * @return the rateValue
     */
    public int getRateValue() {
        return rateValue;
    }

    /**
     * @param rateValue the rateValue to set
     */
    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }

}
