package com.app.reservation.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import org.json.JSONArray;
import org.json.JSONException;

public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    JSONArray jArray = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            pref = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
            editor = pref.edit();
            String string = pref.getString("jsonTable", null);

            try {
                jArray = new JSONArray(string);

                for (int i = 0; i < jArray.length(); i++) {
                    jArray.getJSONObject(i).put("value", true);
                    jArray.getJSONObject(i).put("customer", "");
                }

                pref.edit().putString("jsonTable", jArray.toString()).apply();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("running....");
            //stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

}