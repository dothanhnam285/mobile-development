package com.beast.bkara;

import android.app.Application;

/**
 * Created by Darka on 4/7/2016.
 */
public class Controller extends Application {
    private boolean _loginStatus = false;
    public static final String YOUTUBE_API_KEY = "AIzaSyDyyVofQ_tgdvQEh30ikZ7LipiQbWeLA1g";

    public boolean isLogin() {
        return _loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this._loginStatus = _loginStatus;
    }
}
