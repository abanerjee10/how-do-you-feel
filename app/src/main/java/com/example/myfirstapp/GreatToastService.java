package com.example.myfirstapp;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by arany on 9/10/2017.
 */

public class GreatToastService extends IntentService {

    public GreatToastService() {
        super("Toast Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "That's awesome. Please let us know if problems arise.", Toast.LENGTH_LONG).show();
                NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.cancel(1);
            }
        });

    }
}
