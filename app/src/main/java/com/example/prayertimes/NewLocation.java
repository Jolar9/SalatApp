package com.example.prayertimes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextLatitude = findViewById(R.id.editTextLatitude);
                EditText editTextLongitude = findViewById(R.id.editTextLongitude);

                String latitude = editTextLatitude.getText().toString();
                String longitude = editTextLongitude.getText().toString();

                // Create an intent to send back the latitude and longitude to the main activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("latitude", latitude);
                resultIntent.putExtra("longitude", longitude);
                setResult(Activity.RESULT_OK, resultIntent);
                finish(); // Close the activity and return to the main activity
            }
        });
    }
}