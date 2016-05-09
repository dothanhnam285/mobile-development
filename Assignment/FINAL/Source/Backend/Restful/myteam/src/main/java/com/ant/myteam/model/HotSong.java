/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ant.myteam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Darka
 */
@Entity
@Table(name = "HotSong")
public class HotSong implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long hotSong_id;
   
    @OneToOne
    @JoinColumn(name = "song_id")
    private Song song;
    
    private String hotPoster;

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
     * @return the hotPoster
     */
    public String getHotPoster() {
        return hotPoster;
    }

    /**
     * @param hotPoster the hotPoster to set
     */
    public void setHotPoster(String hotPoster) {
        this.hotPoster = hotPoster;
    }

    /**
     * @return the hotSong_id
     */
    public Long getHotSong_id() {
        return hotSong_id;
    }

    /**
     * @param hotSong_id the hotSong_id to set
     */
    public void setHotSong_id(Long hotSong_id) {
        this.hotSong_id = hotSong_id;
    }
    
}
