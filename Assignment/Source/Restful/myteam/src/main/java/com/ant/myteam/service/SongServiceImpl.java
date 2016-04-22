package com.ant.myteam.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ant.myteam.dao.SongDao;
import com.ant.myteam.model.Song;

@Service
public class SongServiceImpl implements SongService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SongDao songDao;

	public boolean addSong(Song song) {
		return songDao.addSong(song);
	}

	public boolean updateSong(Song song) {
		return songDao.updateSong(song);
	}

	public boolean deleteSong(Song song) {
		return songDao.deleteSong(song);
	}

	public Song findSongById(long songId) {
		return songDao.findSongById(songId);
	}

	public List<Song> findAllSongs() {
		return songDao.findAllSongs();
	}
        
        public List<Song> findSongByName(String name) {
            return songDao.findSongByName(name);
        }

}
