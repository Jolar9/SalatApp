package com.example.prayertimes;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PrayerTimeBroadcast extends BroadcastReceiver {
    private Context context;
    private MediaPlayer mediaPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String ChannelID = "CHANEL_1";
        int NotificationID = intent.getIntExtra("NotificationID", 0);

        // Create an Intent to open Qibla.java activity
        Intent qiblaIntent = new Intent(context, Qibla.class);
        qiblaIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, qiblaIntent, PendingIntent.FLAG_IMMUTABLE);

        String customSoundUri = "android.resource://" + context.getPackageName() +"/raw/audio";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ChannelID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Prayer Time")
                .setContentText("Reminder for next prayer time")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent) // Set the pending intent
                .setAutoCancel(true) // Close the notification when clicked
                .setSound(null) // Set the custom sound
                .addAction(R.drawable.baseline_check_24, "Confirm", pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        // Notify using the builder
        notificationManagerCompat.notify(NotificationID, builder.build()); // an ID for every notification
        playNotificationSound();
    }

    private void playNotificationSound() {
        mediaPlayer = MediaPlayer.create(context, R.raw.audio);
        if (mediaPlayer == null) {
            return; // Handle error: media player not available
        }

        mediaPlayer.setOnCompletionListener(mp -> stopAudio());
        mediaPlayer.start();
    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}




