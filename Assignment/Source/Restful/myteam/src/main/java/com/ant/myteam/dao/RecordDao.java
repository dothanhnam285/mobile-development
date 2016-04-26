/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ant.myteam.dao;

import com.ant.myteam.model.Record;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Darka
 */
@Transactional
@Repository
public class RecordDao {

    @Autowired
    SessionFactory sessionFactory;

    public boolean saveRecord(Record record) {
        try {
            sessionFactory.getCurrentSession().save(record);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Record> findRecordsBySongId(Long songId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Record R WHERE R.song.song_id IN (SELECT S.song_id FROM Song S WHERE S.song_id = :songId)");
        query.setParameter("songId", songId);
        return (List<Record>) query.list();
    }

    public List<Record> findRecordsByUserId(Long userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Record R WHERE R.user.userId IN (SELECT U.userId FROM User U WHERE U.userId = :userId)");
        query.setParameter("userId", userId);
        return (List<Record>) query.list();
    }

}
