package com.shubhamvadhera.www.bcsender;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static Boolean flagConverted = Boolean.FALSE;
    public static String currency;
    public static String amountFrom;
    public static String amountTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button btn = (Button) findViewById(R.id.buttonCancel);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flagConverted) {
            TextView textView = (TextView) findViewById(R.id.textViewResult);
            textView.setText("Dollar amount $" + amountFrom + " converted to " + amountTo + " " + currency);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendBroadcast (View view) {
        Intent intent = new Intent();
        intent.setAction("com.shubhamvadhera.www.bcsender");
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioButtonGroup);
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a target currency", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton radioButtonSelected = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        currency = (String) radioButtonSelected.getText();
        EditText editText = (EditText) findViewById(R.id.editTextAmount);
        amountFrom = editText.getText().toString();
        intent.putExtra("CURRENCY", currency);
        intent.putExtra("AMOUNT", amountFrom);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }
}
