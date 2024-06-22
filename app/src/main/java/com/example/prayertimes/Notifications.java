package com.example.prayertimes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class Notifications {
    private Context context;
    private NotificationManager notificationManager;
    private MediaPlayer mediaPlayer;

    public Notifications(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannelIfNeeded(String channelId, String channelName, int importance) {
        if (notificationManager.getNotificationChannel(channelId) == null) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(String message) {
        final String channelId = "CHANNEL_1";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannelIfNeeded(channelId, "App Channel", NotificationManager.IMPORTANCE_DEFAULT);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Prayer Notifier")
                .setContentText(message)
                .setNumber(3);

        notificationManager.notify(1, notificationBuilder.build());
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