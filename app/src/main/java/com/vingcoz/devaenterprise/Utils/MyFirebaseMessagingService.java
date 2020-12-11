package com.vingcoz.devaenterprise.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vingcoz.devaenterprise.Activities.authentication.SplashScreen;
import com.vingcoz.devaenterprise.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    String LogName = "Firebase123";

    @Override
    public void onMessageReceived(RemoteMessage message) {
        sendMyNotification(message.getNotification().getBody());
    }

    private void sendMyNotification(String message) {

        Log.d(LogName, "Message " + message);
        Intent intent = new Intent(this, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New Message")
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());
    }
}