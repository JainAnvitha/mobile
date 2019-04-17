package com.example.thsensordriverusingasynctask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView x,y,z,log;
    EditText sensorview;
    Integer xParam, yParam, zParam, sParam;
    Button gbtn, cbtn;
    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x = (TextView) findViewById(R.id.tempview);
        y = (TextView) findViewById(R.id.humiview);
        z = (TextView) findViewById(R.id.actview);
        sensorview = (EditText) findViewById(R.id.sensorview);
        log = (TextView) findViewById(R.id.resultview);
        gbtn = (Button)  findViewById(R.id.generatebutton);
        cbtn = (Button) findViewById(R.id.cancelbutton);
        gbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sParam = Integer.parseInt(sensorview.getText().toString());
                Log.i("Values",sParam.toString());
                MyTask myTask = new MyTask();
                myTask.execute(sParam);
            }
        });

        cbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class MyTask extends AsyncTask<Integer, Integer, Void> {
        String logReport = "";
        Integer count = 1;

        @Override
        protected Void doInBackground(Integer... sParam) {
            while(count <= sParam[0]){
                xParam = r.nextInt((100 - 10) + 1) + 10;
                yParam = r.nextInt((70 - 30) + 1) + 30;
                zParam = r.nextInt((1000 - 0) + 1) + 0;
                publishProgress(count);
                count++;
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.wtf("MainActivity", e.getMessage());
                }
            }
            return null;
        }
        protected void onProgressUpdate(Integer... count) {
            super.onProgressUpdate();
            String result = "\nCounter: " + count[0].toString() + "\n" +
                    " Temperature:" + xParam.toString() + "F\n" +
                    " Humidity:" + yParam.toString() + "%\n" +
                    " Activity:"+ zParam.toString();
            logReport = logReport + result;
            x.setText(String.valueOf(xParam));
            y.setText(String.valueOf(yParam));
            z.setText(String.valueOf(zParam));
            log.setMovementMethod(new ScrollingMovementMethod());
            log.setText(logReport);
            Log.i("onProgressUpdate",result);
        }
    }
}

