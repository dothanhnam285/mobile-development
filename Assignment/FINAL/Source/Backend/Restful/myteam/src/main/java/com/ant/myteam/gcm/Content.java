/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ant.myteam.gcm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Darka
 */
public class Content implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private List<String> registration_ids;
    private Map<String,String> data;

    public void addRegId(String regId){
        if(getRegistration_ids() == null)
            setRegistration_ids(new LinkedList<String>());
        getRegistration_ids().add(regId);
    }

    public void createData(String title, String message){
        if(getData() == null)
            setData(new HashMap<String,String>());

        getData().put("title", title);
        getData().put("message", message);
    }

    /**
     * @return the registration_ids
     */
    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    /**
     * @param registration_ids the registration_ids to set
     */
    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

    /**
     * @return the data
     */
    public Map<String,String> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Map<String,String> data) {
        this.data = data;
    }
}
