package com.ant.myteam.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ant.myteam.model.Employee;
import com.ant.myteam.model.Song;

@Repository
@Transactional
public class SongDaoImpl implements SongDao, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	Log log = LogFactory.getLog(SongDaoImpl.class);

	public boolean addSong(Song song) {
		try {
			 sessionFactory.getCurrentSession().save(song);
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateSong(Song song) {
		try {
			 sessionFactory.getCurrentSession().update(song);
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteSong(Song song) {
		try {
			 sessionFactory.getCurrentSession().delete(song);
			 return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Song findSongById(long songId) {
		Song result = new Song();
		try {
			result=(Song) sessionFactory.getCurrentSession().get(Song.class, songId);
			 return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Song> findAllSongs() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Song.class);
		return (List<Song>) criteria.list();
	}

}
