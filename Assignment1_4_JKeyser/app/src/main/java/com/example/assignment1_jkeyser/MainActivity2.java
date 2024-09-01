package com.example.assignment1_jkeyser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    float avg_acclx = 0;
    float avg_accly = 0;
    float avg_acclz = 0;

    float avg_gyrox = 0;
    float avg_gyroy = 0;
    float avg_gyroz = 0;

    float avg_magx = 0;
    float avg_magy = 0;
    float avg_magz = 0;

    float accl_pitch = 0;
    float accl_roll = 0;
    float accl_yaw = 0;

    float gyro_pitch = 0;
    float gyro_roll = 0;
    float gyro_yaw = 0;

    private EditText editTextAcclX, editTextAcclY, editTextAcclZ;
    private EditText editTextGyroX, editTextGyroY, editTextGyroZ;
    private EditText editTextMagX, editTextMagY, editTextMagZ;

    private Button buttonGetOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Receive invitation to open from other activity
        Intent myIntent = getIntent();

        // Store received mean x, y, z axis for accelerometer, gyroscope, magnetometer from other activity
        avg_acclx = myIntent.getFloatExtra("avg_acclx", 0);
        avg_accly = myIntent.getFloatExtra("avg_accly", 0);
        avg_acclz = myIntent.getFloatExtra("avg_acclz", 0);

        avg_gyrox = myIntent.getFloatExtra("avg_gyrox", 0);
        avg_gyroy = myIntent.getFloatExtra("avg_gyroy", 0);
        avg_gyroz = myIntent.getFloatExtra("avg_gyroz", 0);

        avg_magx = myIntent.getFloatExtra("avg_magx", 0);
        avg_magy = myIntent.getFloatExtra("avg_magy", 0);
        avg_magz = myIntent.getFloatExtra("avg_magz", 0);

        gyro_pitch = myIntent.getFloatExtra("gyro_pitch", 0);
        gyro_roll = myIntent.getFloatExtra("gyro_roll", 0);


        // Link widgets to XML
        editTextAcclX = (EditText) findViewById(R.id.editTextAcclX);
        editTextAcclY = (EditText) findViewById(R.id.editTextAcclY);
        editTextAcclZ = (EditText) findViewById(R.id.editTextAcclZ);

        editTextGyroX = (EditText) findViewById(R.id.editTextGyroX);
        editTextGyroY = (EditText) findViewById(R.id.editTextGyroY);
        editTextGyroZ = (EditText) findViewById(R.id.editTextGyroZ);

        editTextMagX = (EditText) findViewById(R.id.editTextMagX);
        editTextMagY = (EditText) findViewById(R.id.editTextMagY);
        editTextMagZ = (EditText) findViewById(R.id.editTextMagZ);

        buttonGetOrientation = (Button) findViewById(R.id.buttonGetOrientation);


        // Set text of widgets to mean values
        editTextAcclX.setText("Accl X Axis: " + Float.toString(avg_acclx));
        editTextAcclY.setText("Accl Y Axis: " + Float.toString(avg_accly));
        editTextAcclZ.setText("Accl Z Axis: " + Float.toString(avg_acclz));

        editTextGyroX.setText("Gyro X Axis: " + Float.toString(avg_gyrox));
        editTextGyroY.setText("Gyro Y Axis: " + Float.toString(avg_gyroy));
        editTextGyroZ.setText("Gyro Z Axis: " + Float.toString(avg_gyroz));

        editTextMagX.setText("Mag X Axis: " + Float.toString(avg_magx));
        editTextMagY.setText("Mag Y Axis: " + Float.toString(avg_magy));
        editTextMagZ.setText("Mag Z Axis: " + Float.toString(avg_magz));



        buttonGetOrientation.setOnClickListener(this);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            // If button is clicked
            case R.id.buttonGetOrientation:Button:
                // Accelerometer measures linear acceleration in m/s^2
                // Gyroscope measures angular velocity in degrees/sec
                // Magnetometer measures magnetic field in uT
                // Pitch is X, Roll is Z, Yaw is Y
                accl_pitch = (float) Math.atan2(-avg_acclx, Math.sqrt(Math.pow(avg_accly, 2) + Math.pow(avg_acclz, 2)));
                // Convert from Radians to Degrees
                accl_pitch = (float) Math.toDegrees(accl_pitch);

                accl_roll = (float) Math.atan2(avg_accly, avg_acclz);
                // Convert from Radians to Degrees
                accl_roll = (float) Math.toDegrees(accl_roll);


                // The Declination Angle at UMBC is approximately 10.98 degrees West
                // float declinationAngle = (float) -10.98;
                // yaw = (float) Math.atan2(Math.sin(declinationAngle), Math.cos(declinationAngle) * Math.sin(pitch) * +Math.tan(roll) * Math.cos(pitch));
                // Convert from Radians to Degrees
                // yaw = (float) Math.toDegrees(yaw);

                accl_yaw = (float) Math.atan2(-avg_magx * Math.cos(accl_roll) + avg_magy * Math.sin(accl_roll), avg_magz * Math.cos(accl_pitch) +
                                 avg_magx * Math.sin(accl_pitch) * Math.sin(accl_roll) + avg_magy * Math.sin(accl_pitch) * Math.cos(accl_roll));
                // Convert from Radians to Degrees
                accl_yaw = (float) Math.toDegrees(accl_yaw);

                // In case over 360 degrees
                gyro_roll = (float) (gyro_roll % 360.0);
                gyro_pitch = (float) (gyro_pitch % 360.0);

                gyro_yaw = (float) Math.atan2(-avg_magx * Math.cos(gyro_roll) + avg_magy * Math.sin(gyro_roll), avg_magz * Math.cos(gyro_pitch) +
                        avg_magx * Math.sin(gyro_pitch) * Math.sin(gyro_roll) + avg_magy * Math.sin(gyro_pitch) * Math.cos(gyro_roll));
                // Convert from Radians to Degrees
                gyro_yaw = (float) Math.toDegrees(accl_yaw);
                gyro_yaw = (float) (gyro_yaw % 360.0);

                // Connecting the 2 activities
                Intent myIntent = new Intent(MainActivity2.this, MainActivity3.class);

                // Sending over pitch, roll, yaw
                myIntent.putExtra("accl_pitch", accl_pitch);
                myIntent.putExtra("accl_roll", accl_roll);
                myIntent.putExtra("accl_yaw", accl_yaw);

                myIntent.putExtra("gyro_pitch", gyro_pitch);
                myIntent.putExtra("gyro_roll", gyro_roll);
                myIntent.putExtra("gyro_yaw", gyro_yaw);


                // Open up other activity
                startActivity(myIntent);


                break;
        }
    }
}