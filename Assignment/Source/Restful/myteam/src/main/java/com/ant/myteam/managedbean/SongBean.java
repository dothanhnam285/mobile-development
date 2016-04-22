package com.ant.myteam.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ant.myteam.model.Song;
import com.ant.myteam.service.SongService;

@Component("songBean")
public class SongBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	Log log = LogFactory.getLog(SongBean.class);
	
	@Autowired
	private SongService songService;
	
	private List<Song> songlistall = new ArrayList<Song>();
	
	private Song song = new Song();
	
	public SongBean() {
		getSong().setTitle("Em cua ngay hom qua");
		getSong().setVideo_id("9tQa5B4iVsA");
	}

	public List<Song> getSonglistall() {
		songlistall = songService.findAllSongs();
		return songlistall;
	}
        
        public List<Song> findSongByName(String name) {
            return songService.findSongByName(name);
        }

	public void setSonglistall(List<Song> songlist) {
		this.songlistall = songlist;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}
	
	
}
