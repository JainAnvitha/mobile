package com.example.torchapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button playBtn;
    SeekBar positionBar;
    SeekBar volumeBar;
    TextView torchText;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightSensorListener;
    public static Camera cam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        torchText = (TextView) findViewById(R.id.torchText);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


        if (lightSensor == null) {
            Toast.makeText(this, "light sensor is not available !", Toast.LENGTH_LONG).show();
            finish();
        }

        lightSensorListener = new SensorEventListener() {
            public void onSensorChanged(SensorEvent sensorEvent) {
                try {

                    if (sensorEvent.values[0] >=10) {
                        torchText.setText("TORCH OFF");
                        Camera.Parameters p = cam.getParameters();
                        cam.setParameters(p);
                        cam.stopPreview();
                        cam.release();

                    } else {
                        cam=Camera.open();
                        Camera.Parameters p = cam.getParameters();
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        torchText.setText("TORCH ON  " + sensorEvent.values[0]);
                        cam.setParameters(p);
                        cam.startPreview();
                    }
                }catch(Exception e){
                    Log.d("Exception", "instance initializer: "+e);
                }

            }

           public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }



    private void releaseCameraAndPreview(Camera c) {
        if (c != null) {
            c.release();
            c = null;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightSensorListener, lightSensor,
                2 * 1000 * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightSensorListener);
    }





}
