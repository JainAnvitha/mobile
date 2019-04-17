package com.shubhamvadhera.www.bcreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shubham on 3/2/2016.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.shubhamvadhera.www.bcsender")) {
            String message1 = intent.getExtras().getString("CURRENCY");
            String message2 = intent.getExtras().getString("AMOUNT");
            PackageManager pm = context.getPackageManager();
            Intent launchIntent = pm.getLaunchIntentForPackage("com.shubhamvadhera.www.bcreceiver");
            launchIntent.putExtra("CURRENCY", message1);
            launchIntent.putExtra("AMOUNT", message2);
            context.startActivity(launchIntent);
        }
    }
}
