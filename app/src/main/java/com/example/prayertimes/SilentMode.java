package com.example.prayertimes;


import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;


public class SilentMode extends BroadcastReceiver {
    private AudioManager audioManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), "Phone is switched off silent mode", Toast.LENGTH_SHORT).show();
        audioManager =(AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }
}
