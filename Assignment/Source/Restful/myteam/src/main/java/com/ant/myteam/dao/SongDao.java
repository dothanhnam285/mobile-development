package com.ant.myteam.dao;

import com.ant.myteam.model.RatingRecord;
import com.ant.myteam.model.RatingSong;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ant.myteam.model.Song;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

@Transactional
@Repository
public class SongDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private SessionFactory sessionFactory;

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
            result = (Song) sessionFactory.getCurrentSession().get(Song.class, songId);
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
    
    public List<Song> findNewSongs() {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Song S ORDER BY S.date_added DESC");
        query.setMaxResults(4);
        return (List<Song>) query.list();
    }

    public List<Song> findSongsByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Song.class);
        criteria.add(Restrictions.like("title_search", "%" + name + "%").ignoreCase());
        return (List<Song>) criteria.list();
    }

    public List<Song> findSongsBySingerName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Song S WHERE S.singer.singer_id IN (SELECT A.singer_id FROM Singer A WHERE LOWER(A.name_search) LIKE LOWER(concat('%', :name, '%')))");
        query.setParameter("name", name);
        return (List<Song>) query.list();
    }

    public void rateSong(RatingSong ratingSong) {
        RatingSong existed = checkExistRatingSong(ratingSong);
        try {
            if (existed == null) {
                sessionFactory.getCurrentSession().save(ratingSong);
            } else {
                existed.setRateValue(ratingSong.getRateValue());
                sessionFactory.getCurrentSession().update(existed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RatingSong checkExistRatingSong(RatingSong ratingSong) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM RatingSong RS WHERE RS.song.song_id = :song_id AND RS.user.userId = :userId");
        query.setParameter("song_id", ratingSong.getSong().getSong_id());
        query.setParameter("userId", ratingSong.getUser().getUserId());
        return (RatingSong) query.uniqueResult();
    }

}
