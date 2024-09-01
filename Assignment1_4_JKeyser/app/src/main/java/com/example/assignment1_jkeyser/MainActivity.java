// Assignment 1: 3-D Orientation Detection: Jackson Keyser


// To import library: Alt + Shift + Enter

// Filers: commonly used in signal processing to remove unwanted frequencies from a signal (or noise)
// High-pass Filters: Allows signals above a certain frequency
// Low-pass Filters: Allows signals below a certain frequency

/*
I/System.out: Accl Size: 900
I/System.out: Gyro Size: 300
I/System.out: Mag Size: 300
I/System.out: Average Accl x: -3.29313
I/System.out: Average Accl y: 6.356038
I/System.out: Average Accl z: 2.2793684
I/System.out: Average Gyro x: -0.10013502
I/System.out: Average Gyro y: 0.11958003
I/System.out: Average Gyro z: -0.14861916
I/System.out: Average Mag x: 4.525625
I/System.out: Average Mag y: 15.822083
I/System.out: Average Mag z: -35.730415
*/




package com.example.assignment1_jkeyser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    // For storing 300 instances of x, y, z coordinates for accelerometer, gyroscope, magnetometer
    ArrayList<ArrayList<Float>> acclArray = new ArrayList<ArrayList<Float>>();
    ArrayList<ArrayList<Float>> gyroArray = new ArrayList<ArrayList<Float>>();
    ArrayList<ArrayList<Float>> magArray = new ArrayList<ArrayList<Float>>();

    // For storing mean x, y, and z values for accelerometer, gyroscope, magnetometer
    private float avg_acclx, avg_accly, avg_acclz;
    private float avg_gyrox, avg_gyroy, avg_gyroz;
    private float avg_magx,avg_magy, avg_magz;

    // For storing the final x, y, and z axis that will be displayed
    private float avgXAxis, avgYAxis, avgZAxis = 0;


    // For storing the values for pitch and roll from the gyroscope
    private float gyro_pitch, gyro_roll = 0;


    // When clicked, runs sensor to get coordinates
    private Button startScanningButton;


    // Stores the coordinates
    private EditText editTextacclCount, editTextgyroCount, editTextmagCount, editTextcollecting300Instances;

    // Provides access to device's hardware sensor
    private SensorManager sensorManager;

    // Represents a single sensor on an Android device
    private Sensor accelerometer;

    private Sensor gyroscope;
    private Sensor magnetometer;

    // Stores float values of coordinates from accelerometer
    private float acclx, accly, acclz = 0;

    private float gyrox, gyroy, gyroz = 0;
    private float magx,magy, magz = 0;

    // Stores the amount of times the accelerometer has changed
    int counter = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        // To use the SensorManager, need to first get a reference to it using the getSystemService()
        //SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE)

        // Requests for the default accelerometer sensor
        //sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // The getDefaultSensor() method takes a single parameter, which is the sensor type you...
        // ... are interested in.
        // Interested in accelerometer
        //accelerometer = (Sensor)sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);




        // Above code was not working with the registerListener
        // Had to Google around, these are slight adjustments to fix it
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


        // Links widgets to XML file. For displaying
        editTextacclCount = (EditText) findViewById(R.id.editTextacclCount);
        editTextgyroCount = (EditText) findViewById(R.id.editTextgyroCount);
        editTextmagCount = (EditText) findViewById(R.id.editTextmagCount);
        editTextcollecting300Instances = (EditText) findViewById(R.id.editTextCollecting300Instances);
        startScanningButton = (Button) findViewById(R.id.startScanningButton);


        editTextcollecting300Instances.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));




        startScanningButton.setOnClickListener(this);


    }
    public void onClick(View v){
        switch(v.getId()){
            // If button is clicked
            case R.id.startScanningButton:Button:
                editTextcollecting300Instances.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));

                // Allows for multiple runs
                acclArray.clear();
                gyroArray.clear();
                magArray.clear();

                gyro_pitch = 0;
                gyro_roll = 0;


                // Method call to register a listener for receiving sensor data updates from an...
                // ... Android device's sensor hardware.
                // Turn on sensors for accelerometer, gyroscope, magnetometer
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
                break;
        }

    }

    @Override
    // When a sensor detects change in value
    public void onSensorChanged(SensorEvent event){
        // System.out.println("onSensorChanged");

        // For storing 1 instance of x, y, and z coordinates for accelerometer, gyroscope, magnetometer
        ArrayList<Float> oneInstanceAccl = new ArrayList<Float>();
        ArrayList<Float> oneInstanceGyro = new ArrayList<Float>();
        ArrayList<Float> oneInstanceMag = new ArrayList<Float>();


        // If accelerometer
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            counter = counter + 1;

            // Accelerometer was gathering data 3x as fast as gyroscope and magnetometer
            // Taking every third instance for the accelerometer corrects this
            if(counter % 3 == 0){
                //System.out.println(counter);
                // Stores new values of coordinates
                acclx = event.values[0];
                accly = event.values[1];
                acclz = event.values[2];

                // Storing the coordinates for the 1 instance
                oneInstanceAccl.add(acclx);
                oneInstanceAccl.add(accly);
                oneInstanceAccl.add(acclz);

                // Storing 1 instance in array of all instances
                acclArray.add(oneInstanceAccl);
            }

        }
        // If gyroscope
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            // Stores new values of coordinates
            gyrox = event.values[0];
            gyroy = event.values[1];
            gyroz = event.values[2];


            // Storing the coordinates for the 1 instance
            oneInstanceGyro.add(gyrox);
            oneInstanceGyro.add(gyroy);
            oneInstanceGyro.add(gyroz);


            // Storing 1 instance in array of all instances
            gyroArray.add(oneInstanceGyro);
        }
        // If magnetometer
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            // Stores new values of coordinates
            magx = event.values[0];
            magy = event.values[1];
            magz = event.values[2];


            // Storing the coordinates for the 1 instance
            oneInstanceMag.add(magx);
            oneInstanceMag.add(magy);
            oneInstanceMag.add(magz);

            // Storing 1 instance in array of all instances
            magArray.add(oneInstanceMag);
        }


        editTextacclCount.setText("Accelerometer: " + new Float (acclArray.size()).toString());
        editTextgyroCount.setText("Gyroscope: " + new Float (gyroArray.size()).toString());
        editTextmagCount.setText("Magnetometer: " + new Float (magArray.size()).toString());


        //System.out.println("Accl Size: "+ acclArray.size());
        //System.out.println("Gyro Size: "+ gyroArray.size());
        //System.out.println("Mag Size: "+ magArray.size());


        if(acclArray.size() >= 100 && gyroArray.size() >= 100 && magArray.size() >= 100){
            editTextcollecting300Instances.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        if(acclArray.size() >= 200 && gyroArray.size() >= 200 && magArray.size() >= 200){
            editTextcollecting300Instances.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
        }


        // If we got the 300 samples
        if(acclArray.size() >= 300 && gyroArray.size() >= 300 && magArray.size() >= 300){
            editTextcollecting300Instances.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

            // Disable the sensors
            sensorManager.unregisterListener(this);

            // Get the mean values of the 300 samples
            getMeanValues();

            // Connecting the 2 activities
            Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);

            // Sending over mean x, y, z axis values for accelerometer, gyroscope, magnetometer
            myIntent.putExtra("avg_acclx", avg_acclx);
            myIntent.putExtra("avg_accly", avg_accly);
            myIntent.putExtra("avg_acclz", avg_acclz);

            myIntent.putExtra("avg_gyrox", avg_gyrox);
            myIntent.putExtra("avg_gyroy", avg_gyroy);
            myIntent.putExtra("avg_gyroz", avg_gyroz);

            myIntent.putExtra("avg_magx", avg_magx);
            myIntent.putExtra("avg_magy", avg_magy);
            myIntent.putExtra("avg_magz", avg_magz);

            myIntent.putExtra("gyro_roll", gyro_roll);
            myIntent.putExtra("gyro_pitch", gyro_pitch);

            // Open up other activity
            startActivity(myIntent);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void getMeanValues(){
        // For storing sums, will then be divided by 300 at the end to obtain the mean
        float acclXtotal = 0;
        float acclYtotal = 0;
        float acclZtotal = 0;

        float gyroXtotal = 0;
        float gyroYtotal = 0;
        float gyroZtotal = 0;

        float magXtotal = 0;
        float magYtotal = 0;
        float magZtotal = 0;

        // Looping through the 300 samples
        for(int i = 0; i < 300; i++){
            acclXtotal = acclXtotal + acclArray.get(i).get(0);
            acclYtotal = acclYtotal + acclArray.get(i).get(1);
            acclZtotal = acclZtotal + acclArray.get(i).get(2);

            gyroXtotal = gyroXtotal + gyroArray.get(i).get(0);
            gyroYtotal = gyroYtotal + gyroArray.get(i).get(1);
            gyroZtotal = gyroZtotal + gyroArray.get(i).get(2);

            magXtotal = magXtotal + magArray.get(i).get(0);
            magYtotal = magYtotal + magArray.get(i).get(1);
            magZtotal = magZtotal + magArray.get(i).get(2);

            // Multiply by 1/3 because every instance is gathered every 1/5 of a second
            gyro_roll += (float) (gyroArray.get(i).get(0) * (1.0/5));
            gyro_pitch += (float) (gyroArray.get(i).get(1) * (1.0/5));
        }

        avg_acclx = acclXtotal / 300;
        avg_accly = acclYtotal / 300;
        avg_acclz = acclZtotal / 300;

        avg_gyrox = gyroXtotal / 300;
        avg_gyroy = gyroYtotal / 300;
        avg_gyroz = gyroZtotal / 300;

        avg_magx = magXtotal / 300;
        avg_magy = magYtotal / 300;
        avg_magz = magZtotal / 300;

        //System.out.println("Average Accl x: " + avg_acclx);
        //System.out.println("Average Accl y: " + avg_accly);
        //System.out.println("Average Accl z: " + avg_acclz);

        //System.out.println("Average Gyro x: " + avg_gyrox);
        //System.out.println("Average Gyro y: " + avg_gyroy);
        //System.out.println("Average Gyro z: " + avg_gyroz);

        //System.out.println("Average Mag x: " + avg_magx);
        //System.out.println("Average Mag y: " + avg_magy);
        //System.out.println("Average Mag z: " + avg_magz);

        //System.out.println("Gyro Roll: " + gyro_roll);
        //System.out.println("Gyro Pitch: " + gyro_pitch);



    }

}