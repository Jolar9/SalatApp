package com.example.prayertimes;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        // Extract the notification from the intent
//        NotificationCompat.Builder builder = intent.getParcelableExtra("notification");
//
//        // Display the notification
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(MainActivity.NOTIFICATION_ID, builder.build());

        // Extract the notification from the intent
        Notification notification = intent.getParcelableExtra("notification");

        // Create a NotificationCompat.Builder from the extracted notificationManager
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.qiblaicon)
                .setContentTitle("Notification Title")
                .setContentText("Notification Content Text")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}
