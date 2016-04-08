package com.beast.bkara;

import android.app.Application;

/**
 * Created by Darka on 4/7/2016.
 */
public class Controller extends Application {
    private boolean _loginStatus = false;

    public boolean isLogin() {
        return _loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this._loginStatus = _loginStatus;
    }
}
