package com.example.helpme;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MedicineAlarm extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.medicine_alarm_activity);
        TextView tv=(TextView)findViewById(R.id.AlarmActtextView) ;
        TextView name = (TextView) findViewById(R.id.medname);
        TextView inst = (TextView) findViewById(R.id.instructions);

        Intent i=getIntent();
        int a=i.getExtras().getInt("AlarmActivityid");
        Database dh= new Database(this);
        ArrayList ar=dh.getReminder(a);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        tv.setText(date);
        name.setText(ar.get(1).toString());
        String instructions = ar.get(2).toString();
        if(instructions.compareTo("")==0)
            inst.setVisibility(View.GONE);
        else
            inst.setText(instructions);

        Button stop=(Button)findViewById(R.id.stop);

        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MedicineAlarmWake.ringtone.stop();
                finish();

            }
        });




    }

    @Override
    public void onBackPressed() {
        MedicineAlarmWake.ringtone.stop();
        finish();
    }
}

