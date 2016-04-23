package com.ant.myteam.service;

import java.util.List;

import com.ant.myteam.model.Song;

public interface SongService {

	public boolean addSong(Song song);
	
	public boolean updateSong(Song song);
	
	public boolean deleteSong(Song song);
	
	public Song findSongById(long songId);
	
	public List<Song> findAllSongs();
        
        public List<Song> findSongsByName(String name);
        
        public List<Song> findSongsBySingerName(String name);
	
}
