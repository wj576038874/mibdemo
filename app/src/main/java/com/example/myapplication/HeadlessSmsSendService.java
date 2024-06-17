package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

public class HeadlessSmsSendService extends Service {

    private static final String TAG = "HeadlessSmsSendService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals("android.intent.action.RESPOND_VIA_MESSAGE")) {
                handleSendMessage(intent);
            }
        }
        return START_NOT_STICKY;
    }

    private void handleSendMessage(Intent intent) {
        String destinationAddress = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        String messageBody = intent.getStringExtra(Intent.EXTRA_TEXT);

        if (destinationAddress != null && messageBody != null) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(destinationAddress, null, messageBody, null, null);
            Log.d(TAG, "Sent SMS to " + destinationAddress + ": " + messageBody);
        } else {
            Log.e(TAG, "Failed to send SMS. Destination address or message body is missing.");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
