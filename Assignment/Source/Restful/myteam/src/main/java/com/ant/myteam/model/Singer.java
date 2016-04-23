/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ant.myteam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Darka
 */

@Entity
@Table(name="Singer")
public class Singer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long singer_id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = true)
    private String name_search;
    
    @Column(nullable = true)
    private String poster;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "singer_id")
    private Collection<Song> songs;

    /**
     * @return the singer_id
     */
    public Long getSinger_id() {
        return singer_id;
    }

    /**
     * @param singer_id the singer_id to set
     */
    public void setSinger_id(Long singer_id) {
        this.singer_id = singer_id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * @return the songs
     */
    public Collection<Song> getSongs() {
        return songs;
    }

    /**
     * @param songs the songs to set
     */
    public void setSongs(Collection<Song> songs) {
        this.songs = songs;
    }
    
}
