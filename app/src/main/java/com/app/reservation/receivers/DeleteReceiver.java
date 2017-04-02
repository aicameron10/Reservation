package com.app.reservation.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.reservation.services.BackgroundService;

public class DeleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BackgroundService.class);
        context.startService(background);
    }

}