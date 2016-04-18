package com.ant.myteam.controller;

import com.ant.myteam.managedbean.SongBean;
import com.ant.myteam.model.Song;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BkaraController {

    @Autowired
    private SongBean songBean;

    @RequestMapping(value = "/songlist/all", method = RequestMethod.GET)
    public List<Song> getListSongAll() {
        return songBean.getSonglistall();
    }
    
    @RequestMapping(value = "/song", method = RequestMethod.GET)
    public Song getSong() {
        return songBean.getSong();
    }

}
