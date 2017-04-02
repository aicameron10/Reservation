package com.app.reservation.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.app.reservation.R;
import com.app.reservation.fragments.HomeFragment;
import com.app.reservation.fragments.TableFragment;
import com.app.reservation.model.NavItem;
import com.app.reservation.receivers.DeleteReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Toolbar toolbar;
    private static long back_pressed;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);


        Intent alarm = new Intent(this, DeleteReceiver.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmRunning) {
            loadJSONFromAsset();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 600000, pendingIntent);
        }

        displayView(0);

        loadDraws();
    }

    public void loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("table_map.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        JSONArray finalArray = null;
        try {
            JSONArray j = new JSONArray(json);
            finalArray = new JSONArray();
            for (int i = 0; i < j.length(); i++) {

                Boolean table = j.getBoolean(i);
                JSONObject job = new JSONObject();
                job.put("value", table);
                job.put("customer", "");
                finalArray.put(job);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        pref.edit().putString("jsonTable", finalArray.toString()).apply();

    }


    public void loadDraws() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

        };

        Drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void loadDrawsDisplay() {
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
    }

    public void loadDrawsHide() {
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (id == R.id.nav_customers) {
            displayView(0);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        NavItem nav = new NavItem();
        // Handle action bar actions click
        switch (item.getItemId()) {
            case android.R.id.home:
                String navi = nav.getPage();
                switch (navi) {
                    case "Home":
                        displayView(0);
                        return true;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Displaying fragment view for selected nav drawer list item
     */
    public void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        String Tag = "";
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                Tag = "HomeFragment";
                break;
            case 1:
                fragment = new TableFragment();
                Tag = "TableFragment";
                break;

            default:
                break;
        }

        if (fragment != null) {

            try {
                InputMethodManager input = (InputMethodManager) this
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(new View(this).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                e.printStackTrace();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment, Tag).commit();
        } else {
            Log.e("HomeActivity", "Error in creating fragment");
        }
    }


    @Override
    public void onBackPressed() {

        NavItem nav = new NavItem();
        String navi = nav.getPage();
        switch (navi) {
            case "HomePage":
                if (back_pressed + 2000 > System.currentTimeMillis()) {
                    super.onBackPressed();
                } else {
                    Toast toast = Toast.makeText(getBaseContext(), "Press back once again to exit application!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    back_pressed = System.currentTimeMillis();
                }
                return;
            case "Home":
                displayView(0);
                return;
            case "Table":
                displayView(1);
                return;

        }


    }

    public void closeKeyBoard() {

        try {

            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(new View(this).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}