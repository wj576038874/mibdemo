package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.WAP_PUSH_DELIVER")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // Extract the MMS data and process it
                processMms(context, intent);
            }
        }
    }

    private void processMms(Context context, Intent intent) {
        // Add your logic to process the received MMS
        // For example, you might want to save the MMS to a database,
        // display a notification, etc.
        Log.d("MmsReceiver", "Received an MMS message");
    }
}
