/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ant.myteam.dao;

import com.ant.myteam.model.User;
import com.ant.myteam.model.UserGCM;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VINH
 */
@Transactional
@Repository
public class UserDao {

    @Autowired
    SessionFactory sessionFactory;

    public boolean save(User user) {
        try {
            sessionFactory.getCurrentSession().save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(User user){
        try{
            sessionFactory.getCurrentSession().update(user);
            return true;
        } catch( Exception e ){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public User checkUserExisted(User user) {
        try {
            Query query;
            if (user.getPassword() == null || user.getPassword().equals("")) {
                query = sessionFactory.getCurrentSession().createQuery("FROM User WHERE username = :username");
                query.setParameter("username", user.getUserName());
            } else {
                query = sessionFactory.getCurrentSession().createQuery("FROM User WHERE username = :username AND password = :password");
                query.setParameter("username", user.getUserName());
                query.setParameter("password", user.getPassword());
            }

            //if( query.uniqueResult() != null )
            return (User) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveGCM(UserGCM userGCM) {
        try {
            sessionFactory.getCurrentSession().save(userGCM);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteGCM(UserGCM userGCM) {
        try {
            sessionFactory.getCurrentSession().delete(userGCM);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public UserGCM checkUserGCMExisted(Long userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM UserGCM WHERE userId = :userId");
        query.setParameter("userId", userId);
        return (UserGCM) query.uniqueResult();
    }
}
