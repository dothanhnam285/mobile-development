package com.beast.bkara;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beast.bkara.dialogfragments.LoginDialogFragment;
import com.beast.bkara.dialogfragments.SaveRecordDialogFragment;
import com.beast.bkara.dialogfragments.SignUpDialogFragment;
import com.beast.bkara.fragments.*;
import com.beast.bkara.model.User;
import com.beast.bkara.util.BkaraService;
import com.beast.bkara.util.SongSearchView;
import com.beast.bkara.util.UploadToSoundCloudTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        BlankFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        SongsFragment.OnFragmentInteractionListener,
        SongListFragment.OnFragmentInteractionListener,
        GenresFragment.OnFragmentInteractionListener,
        KaraokeFragment.OnFragmentInteractionListener,
        RecordsFragment.OnFragmentInteractionListener,
        LoginDialogFragment.OnLoginDialogFragmentInteractionListener,
        SignUpDialogFragment.OnSignUpDialogFragmentInteractionListener,
        SaveRecordDialogFragment.OnFragmentInteractionListener {

    private RelativeLayout mLayout;
    private LayoutInflater mLayoutInflater;
    private PopupWindow mPopupWindow;
    private DialogFragment loginFragment,signUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up universal image loader
        ImageLoaderConfiguration imageLoaderConfig = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(imageLoaderConfig);

        // Get views
        mLayout = (RelativeLayout) findViewById(R.id.content_main_rl_layout);

        // Setup application action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        // Setup navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set the default view to Home item
        displayView(R.id.nav_home);

    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);

        // Double back pressed
        if( doubleBackToExitPressedOnce ){
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.press_twice_exit_app_msg, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        // Handle SearchView behaviour
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SongSearchView searchView = (SongSearchView) MenuItemCompat.getActionView(searchItem);

        final MenuItem filterBySongName = menu.findItem(R.id.filter_by_song_name);
        final MenuItem filterBySingerName = menu.findItem(R.id.filter_by_singer_name);

        searchView.setIconifiedByDefault(true);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.findItem(R.id.action_filter).setVisible(true);
            }
        });

        searchView.setOnSearchViewCollapsedEventListener(new SongSearchView.OnSearchViewCollapsedEventListener() {
            @Override
            public void onSearchViewCollapsed() {
                menu.findItem(R.id.action_filter).setVisible(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599

                if (filterBySongName.isChecked()) {
                    Log.d("BKARA", "SEARCH BY SONG NAME");
                    displayCustomFragment(SongListFragment.newInstance(BkaraService.SongSearchFilter.SONG_NAME, query), "SEARCH SONG");
                } else {
                    Log.d("BKARA", "SEARCH BY SINGER NAME");
                    displayCustomFragment(SongListFragment.newInstance(BkaraService.SongSearchFilter.SINGER_NAME, query), "SEARCH SONG");
                }

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.filter_by_song_name:
                item.setChecked(true);
                return true;
            case R.id.filter_by_singer_name:
                item.setChecked(true);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());
        return true;
    }

    /**
     * Display view based on selected navigation item
     * @param viewId selected navigation item's id
     */
    private void displayView(int viewId) {
        Fragment fragment = new BlankFragment();
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                title = "BKara";
                break;
            case R.id.nav_songs:
                fragment = new SongsFragment();
                title = "Songs";
                break;
            case R.id.nav_genre:
                fragment = new GenresFragment();
                title = "Genres";
                break;
            case R.id.nav_playlist:
                //fragment = new PlaylistFragment();
                title = "Playlist";
                break;
            case R.id.nav_history:
                //fragment = new HistoryFragment();
                title = "History";
                break;
            case R.id.nav_records:
                fragment = RecordsFragment.newInstance(false, null);
                title = "Records";
                break;
            case R.id.nav_setting:
                break;
            case R.id.nav_about:
                break;
        }

        // Change fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // Set page title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        // Close navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void displayCustomFragment(Fragment fragment, String title) {
        // Change fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // Set page title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void displayPopup(int layoutId) {
        // Inflate the layout
        mLayoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) mLayoutInflater.inflate(layoutId, null);

        // Get screen width and height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        // Create popup window
        mPopupWindow = new PopupWindow(container, (int) (height * 0.7), (int) (width * 0.8), true);
        mPopupWindow.showAtLocation(mLayout, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void login(View v) {
        loginFragment = LoginDialogFragment.newInstance(null, null);
        loginFragment.show(getSupportFragmentManager(),"LOGIN");
}

    @Override
    public void onLoginDialogFragmentInteraction(Uri uri) {

    }

    @Override
    public void onOpenSignUpForm() {
        signUpFragment = SignUpDialogFragment.newInstance(null, null);
        signUpFragment.show(getSupportFragmentManager(),"SIGN UP");
    }


    @Override
    public void onLoginSuccessfully(User user) {

        if( loginFragment != null && loginFragment.getDialog() != null )
            loginFragment.dismiss();
        else {
            loginFragment = (DialogFragment) getSupportFragmentManager().findFragmentByTag("LOGIN");
            loginFragment.getDialog().dismiss();
        }

        setUserInfoAfterLoginSuccessfully(user);
    }


    @Override
    public void onSignUpSuccessfully(User user) {
        if( loginFragment != null && loginFragment.getDialog() != null )
            loginFragment.dismiss();

        if( signUpFragment != null && signUpFragment.getDialog() != null )
            signUpFragment.dismiss();

        setUserInfoAfterLoginSuccessfully(user);
    }

    private void setUserInfoAfterLoginSuccessfully(User user) {
        final TextView welcome, login , logout;

        welcome = (TextView) findViewById(R.id.nav_header_tv_welcome);
        login = (TextView) findViewById(R.id.nav_header_tv_login);
        logout = (TextView) findViewById(R.id.nav_header_tv_logout);

        if ( welcome == null || login == null || logout == null)
            return;

        login.setVisibility(View.GONE);

        final Controller controller = (Controller) getApplicationContext();
        // it also means user has logon -> isLogin() returns true
        controller.setCurrUser(user);

        welcome.setText("Hi, " +user.getUserName());
        welcome.setVisibility(View.VISIBLE);

        logout.setVisibility(View.VISIBLE);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    private void logout() {
        Controller controller = (Controller) getApplicationContext();
        // isLogin() returns false
        controller.setCurrUser(null);

        findViewById(R.id.nav_header_tv_welcome).setVisibility(View.GONE);
        findViewById(R.id.nav_header_tv_login).setVisibility(View.VISIBLE);
        findViewById(R.id.nav_header_tv_logout).setVisibility(View.GONE);


    }
}
