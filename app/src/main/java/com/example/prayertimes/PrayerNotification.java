package com.example.prayertimes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PrayerNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String ChannelID = "CHANEL_1";
        int NotificationID = intent.getIntExtra("NotificationID", 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ChannelID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Prayer Time")
                .setContentText("Reminder for next prayer time")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        //create the channel
        notificationManagerCompat.notify(NotificationID, builder.build()); // an ID for every notification
    }
}

