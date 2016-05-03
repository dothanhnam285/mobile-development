package com.ant.myteam.controller;

import com.ant.myteam.dao.RecordDao;
import com.ant.myteam.dao.SongDao;
import com.ant.myteam.dao.UserDao;
import com.ant.myteam.gcm.Content;
import com.ant.myteam.gcm.POST2GCM;
import com.ant.myteam.model.RatingRecord;
import com.ant.myteam.model.RatingSong;
import com.ant.myteam.model.Record;
import com.ant.myteam.model.Song;
import java.text.Normalizer;
import java.util.ArrayList;
import com.ant.myteam.model.User;
import com.ant.myteam.model.UserGCM;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class BkaraController {

    public String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    private String SERVER_API_KEY = "AIzaSyCwsKIPZ6SBCXaS0O0yW5DJVx57Kpm5e-Y";

    @Autowired
    private SongDao songDao;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/songlist/all", method = RequestMethod.GET)
    public List<Song> getListSongAll() {
        return songDao.findAllSongs();
    }

    @RequestMapping(value = "/songlist/new", method = RequestMethod.GET)
    public List<Song> getListSongNew() {
        return songDao.findNewSongs();
    }

    @RequestMapping(value = "/songlist/search/songname/{songname}", method = RequestMethod.GET)
    public List<Song> findSongsByName(@PathVariable("songname") String songName) {
        return songDao.findSongsByName(songName);
    }

    @RequestMapping(value = "/songlist/search/singername/{singername}", method = RequestMethod.GET)
    public List<Song> findSongsBySingerName(@PathVariable("singername") String singerName) {
        return songDao.findSongsBySingerName(singerName);
    }

    @RequestMapping(value = "/recordlist/song/{songid}", method = RequestMethod.GET)
    public List<Record> findRecordsBySongId(@PathVariable("songid") Long songId) {
        return recordDao.findRecordsBySongId(songId);
    }

    @RequestMapping(value = "/recordlist/user/{userid}", method = RequestMethod.GET)
    public List<Record> findRecordsByUserId(@PathVariable("userid") Long userId) {
        return recordDao.findRecordsByUserId(userId);
    }

    @RequestMapping(value = "/saverecord", method = RequestMethod.POST)
    @ResponseBody
    public Long saveRecord(@RequestBody Record record) {
        Record rec = recordDao.saveRecord(record);
        if (rec != null)
            return rec.getRecordId();
        else
            return null;
        
    }

    @RequestMapping(value = "/update/song", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateSong(@RequestBody Song song) {
        songDao.updateSong(song);
    }

    @RequestMapping(value = "/update/record", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateRecord(@RequestBody Record record) {
        recordDao.updateRecord(record);
    }

    @RequestMapping(value = "/rate/song", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void rateSong(@RequestBody RatingSong ratingSong) {
        songDao.rateSong(ratingSong);
    }

    @RequestMapping(value = "/rate/record", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void rateRecord(@RequestBody RatingRecord ratingRecord) {
        recordDao.rateRecord(ratingRecord);
    }

    @RequestMapping(value = "/registerGCM", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void registerGCM(@RequestBody UserGCM userGCM) {
        userDao.saveGCM(userGCM);
    }

    @RequestMapping(value = "/unregisterGCM", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void unregisterGCM(@RequestBody UserGCM userGCM) {
        userDao.deleteGCM(userGCM);
    }

    @RequestMapping(value = "/sendnoti/{senderId}/{receiverId}/{message}", method = RequestMethod.GET)
    @ResponseBody
    public String sendNoti(@PathVariable("senderId") Long senderId, @PathVariable("receiverId") Long receiverId, @PathVariable("message") String message) {
        UserGCM sender = userDao.checkUserGCMExisted(senderId);
        UserGCM receiver = userDao.checkUserGCMExisted(receiverId);
//        && !sender.getRegisterId().equals(receiver.getRegisterId())
        if (sender != null && receiver != null && !sender.getRegisterId().equals(receiver.getRegisterId())) {
            Content content = new Content();
            content.addRegId(receiver.getRegisterId());
            content.createData("BKara", message);
            POST2GCM.post(SERVER_API_KEY, content);
            return "push noti";
        } else {
            return "failed to push noti";
        }
    }

    @RequestMapping(value = "/update/user", method = RequestMethod.POST)
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        System.out.println("Update User " + user.getUserName());
        if (userDao.update(user)) {
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<User> signUp(@RequestBody User user) {
        System.out.println("Creating User " + user.getUserName());
        if (userDao.checkUserExisted(user) == null) {
            userDao.save(user);
            return new ResponseEntity<User>(/*"Sign up successfully !!!"*/user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<User>(/*"User is already existed !"*/user, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> checkUserExisted(@RequestBody User user) {
        System.out.println("Check User " + user.getUserName());
        if ((user = userDao.checkUserExisted(user)) == null) {
            return new ResponseEntity<User>(/*"User not found . Please try again !"*/user, HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<User>(/*"Login successfully !!!"*/user, HttpStatus.OK);
        }
    }

//    @RequestMapping(value = "/user/", method = RequestMethod.POST)
//    public ResponseEntity<Void> createUser(@RequestBody User user,    UriComponentsBuilder ucBuilder) {
//        System.out.println("Creating User " + user.getName());
//
//        if (userService.isUserExist(user)) {
//            System.out.println("A User with name " + user.getName() + " already exist");
//            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
//        }
//
//        userService.saveUser(user);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
//        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//    }
}
