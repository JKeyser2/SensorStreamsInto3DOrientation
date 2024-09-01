package com.example.assignment1_jkeyser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity3 extends AppCompatActivity {

    private EditText editTextAcclpitch, editTextAcclroll, editTextAcclyaw;
    private EditText editTextGyropitch, editTextGyroroll, editTextGyroyaw;

    float Acclpitch, Acclroll, Acclyaw;
    float Gyropitch, Gyroroll, Gyroyaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Receive invitation to open from other activity
        Intent myIntent = getIntent();

        // Store pitch, roll, yaw
        Acclpitch = myIntent.getFloatExtra("accl_pitch", 0);
        Acclroll = myIntent.getFloatExtra("accl_roll", 0);
        Acclyaw = myIntent.getFloatExtra("accl_yaw", 0);
        Gyropitch = myIntent.getFloatExtra("gyro_pitch", 0);
        Gyroroll = myIntent.getFloatExtra("gyro_roll", 0);
        Gyroyaw = myIntent.getFloatExtra("gyro_yaw", 0);


        // Link widgets to XML
        editTextAcclpitch = (EditText) findViewById(R.id.editTextAcclpitch);
        editTextAcclroll = (EditText) findViewById(R.id.editTextAcclroll);
        editTextAcclyaw = (EditText) findViewById(R.id.editTextAcclyaw);
        editTextGyropitch = (EditText) findViewById(R.id.editTextGyropitch);
        editTextGyroroll = (EditText) findViewById(R.id.editTextGyroroll);
        editTextGyroyaw = (EditText) findViewById(R.id.editTextGyroyaw);

        // Set text of widgets to mean values
        editTextAcclpitch.setText("Pitch (X): " + Float.toString(Acclpitch));
        editTextAcclroll.setText("Roll (Z): " + Float.toString(Acclroll));
        editTextAcclyaw.setText("Yaw (Y): " + Float.toString(Acclyaw));
        editTextGyropitch.setText("Pitch (X): " + Float.toString(Gyropitch));
        editTextGyroroll.setText("Roll (Z): " + Float.toString(Gyroroll));
        editTextGyroyaw.setText("Yaw (Y): " + Float.toString(Gyroyaw));
    }
}