package com.shubhamvadhera.www.bcsender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

/**
 * Created by Shubham on 3/2/2016.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.shubhamvadhera.www.bcreceiver")) {
            MainActivity.currency = intent.getExtras().getString("CURRENCY");
            MainActivity.amountTo = intent.getExtras().getString("AMOUNT");
            MainActivity.flagConverted = true;
        }
    }
}
