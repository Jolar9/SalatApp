package com.example.prayertimes;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.concurrent.TimeUnit;


public class SettingsFragment extends PreferenceFragment {

    // list prefs
    public static final String PREF_JURISTIC = "juristic";
    public static final String PREF_CALC = "calculation";
    public static final String PREF_LATITUDE = "latitude";
    public static final String PREF_TIME = "time";
    public static final String PREF_SILENT = "silent";
    private static final int CALLBACK_CODE = 0;

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    private int RG1 = 0;
    private int RG2 = 4;
    private int RG3 = 0;
    private int RG4 = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefrences);
        // Find the preference and set its click listener
        Preference silentPref = findPreference("silent");
        silentPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showTimePickerDialog();
                return true;
            }
        });
        //Listener for all the groups
        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(PREF_JURISTIC)) {
                    ListPreference juristicPref = (ListPreference) findPreference(key); //if you need the prefrence key similar to find by ID
                    RG1 = Integer.valueOf((juristicPref.getValue()));
                    Toast.makeText(getActivity(), "Juristic method updated", Toast.LENGTH_SHORT).show();
                }
                if (key.equals(PREF_CALC)) {
                    ListPreference calculatePref = (ListPreference) findPreference(key);
                    RG2 = Integer.valueOf((calculatePref.getValue()));

                    Toast.makeText(getActivity(),"Calculation convention updated" , Toast.LENGTH_SHORT).show();
                }
                if (key.equals(PREF_LATITUDE)) {
                    ListPreference latitudePref = (ListPreference) findPreference(key);
                    RG3 = Integer.valueOf((latitudePref.getValue()));

                    Toast.makeText(getActivity(), "Latitude adjustment updated", Toast.LENGTH_SHORT).show();

                }
                if (key.equals(PREF_TIME)) {
                    ListPreference timePref = (ListPreference) findPreference(key);
                    RG4 = Integer.valueOf((timePref.getValue()));

                    Toast.makeText(getActivity(), "Time format updated", Toast.LENGTH_SHORT).show();
                }
                if (key.equals(PREF_SILENT)) {
//                    EditTextPreference editText = (EditTextPreference) findPreference(key);
//                    String time = editText.getText();
//                    long timeInMillis = Long.valueOf(time);
//                    silencePhone(timeInMillis);


                    // Find the preference and set its click listener
                    Preference silentPref = findPreference("silent");
                    silentPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            showTimePickerDialog();
                            return true;
                        }
                    });
                }
            }
        };

    }

    private void showTimePickerDialog() {
        // Inflate the custom layout for TimePicker
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_time_picker_layout, null);
        TimePicker timePicker = view.findViewById(R.id.timePicker);

        // Show the TimePicker dialog
        new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Select Time")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle TimePicker selection here
                        int hour = timePicker.getCurrentHour();
                        int minute = timePicker.getCurrentMinute();
                        long timeInMillis = (hour * 60 + minute) * 60 * 1000; // Convert to milliseconds
                        silencePhone(timeInMillis);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CALLBACK_CODE) {
            Toast.makeText(getActivity(), "Acccess granted", Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void silencePhone(long timeInMillis) {
        NotificationManager notificationManager = (NotificationManager) getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager.isNotificationPolicyAccessGranted()) {
            AudioManager audioManager = (AudioManager) getActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

            Intent intent = new Intent(getActivity(), SilentMode.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (timeInMillis * 60000), pendingIntent);
            long hours = TimeUnit.MILLISECONDS.toHours(timeInMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis) % 60;

            String message = String.format("Phone will be silent for %02d:%02d", hours, minutes);
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        } else {
            // Ask the user to grant access
            Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivityForResult(intent, CALLBACK_CODE);
        }
    }

    public int getRG1() {
        return RG1;
    }

    public int getRG2() {
        return RG2;
    }

    public int getRG3() {
        return RG3;
    }

    public int getRG4() {
        return RG4;
    }

}
