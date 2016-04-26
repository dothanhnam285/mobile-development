package com.ant.myteam.controller;

import com.ant.myteam.dao.RecordDao;
import com.ant.myteam.dao.UserDao;
import com.ant.myteam.managedbean.SongBean;
import com.ant.myteam.model.Record;
import com.ant.myteam.model.Song;
import java.text.Normalizer;
import java.util.ArrayList;
import com.ant.myteam.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
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

@RestController
public class BkaraController {

    public String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    @Autowired
    private SongBean songBean;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/songlist/all", method = RequestMethod.GET)
    public List<Song> getListSongAll() {
        return songBean.getSonglistall();
    }

    @RequestMapping(value = "/songlist/search/songname/{songname}", method = RequestMethod.GET)
    public List<Song> findSongsByName(@PathVariable("songname") String songName) {
        return songBean.findSongsByName(songName);
    }

    @RequestMapping(value = "/songlist/search/singername/{singername}", method = RequestMethod.GET)
    public List<Song> findSongsBySingerName(@PathVariable("singername") String singerName) {
        return songBean.findSongsBySingerName(singerName);
    }

    @RequestMapping(value = "/recordlist/song/{songid}", method = RequestMethod.GET)
    public List<Record> findRecordsBySongId(@PathVariable("songid") Long songId) {
        return recordDao.findRecordsBySongId(songId);
    }

    @RequestMapping(value = "/recordlist/user/{userid}", method = RequestMethod.GET)
    public List<Record> findRecordsByUserId(@PathVariable("userid") Long userId) {
        return recordDao.findRecordsByUserId(userId);
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
