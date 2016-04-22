package com.ant.myteam.controller;

import com.ant.myteam.managedbean.SongBean;
import com.ant.myteam.model.Song;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BkaraController {

    public String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    @Autowired
    private SongBean songBean;
    
    @RequestMapping(value = "/songlist/all", method = RequestMethod.GET)
    public List<Song> getListSongAll() {
        return songBean.getSonglistall();
    }

    @RequestMapping(value = "/songlist/search/name/{songname}", method = RequestMethod.GET)
    public List<Song> findSongByName(@PathVariable("songname") String songName) {
        return songBean.findSongByName(songName);
    }


}
