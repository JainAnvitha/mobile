package com.shubhamvadhera.www.bcreceiver;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
//import org.json.*;

import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static String currency;
    public static String amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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
    protected void onStart() {
        super.onStart();
        currency = getIntent().getExtras().getString("CURRENCY");
        amount = getIntent().getExtras().getString("AMOUNT");
        TextView tv = (TextView)findViewById(R.id.textViewAmount);

        tv.setText("Dollar Amount: $" + amount);
        tv = (TextView)findViewById(R.id.textViewCurrency);
        tv.setText("Convert To: " + currency);
        Log.v("**resposne**", "iam in onstart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
 public String receivedResponse()
 {
     URL urlForGetRequest = null;
     String apiResponse = null;
     try {
         urlForGetRequest = new URL("https://api.openrates.io/latest?base=USD");
         String readLine = null;
         HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
         conection.setRequestMethod("GET");
         int responseCode = conection.getResponseCode();

         if (responseCode == HttpURLConnection.HTTP_OK)
         {

             BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
             StringBuffer response = new StringBuffer();
             while ((readLine = in.readLine()) != null)
             {
                 response.append(readLine);
             }
             in.close();

             apiResponse = response.toString();

         }

     } catch (IOException e) {
         e.printStackTrace();

     }

     return apiResponse;

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

    public double GetConversionRate(String api_Response, String amount)
    {



        Object sObject = null;
        Double convertedValue=0.0;

        String currency_type = " ";
        JSONParser myparser = new JSONParser();
        try
        {

        if(currency.equals("Indian Rupee"))
        {
            currency_type = "INR";

        }
        else if(currency.equals("British Pound"))
        {
            currency_type = "GBP";

        }
        else if(currency.equals("Euro"))
        {
            currency_type = "EUR";

        }


            Log.v("**resposne**", "stuckhere");

            Log.v("**resposneinme**", api_Response.toString());

            Log.v("**resposneinme**", amount.toString());

            JSONObject jobj = new JSONObject(api_Response);
                    // JSONObject jobj = (JSONObject)myparser.parse(api_Response);
            Log.v("**resposneinme**", api_Response.toString());

            Double convert= 0.0;

            JSONObject myRateObj = (JSONObject) jobj.get("rates");
            Log.v("**resposneinme**", String.valueOf(myRateObj));


            if(currency_type=="INR")
            {
                Log.v("**resposneinme**", "ok");
              convert = myRateObj.getDouble("INR");
                Log.v("**resposneinme**", convert.toString());
            }
            else if(currency_type=="GBP")
            {
              convert = myRateObj.getDouble("GBP");
                Log.v("**resposneinme**", convert.toString());
            }
            else if(currency_type=="EUR")
            {
             convert = myRateObj.getDouble("EUR");
                Log.v("**resposneinme**", convert.toString());
            }

            convertedValue = convert * Double.parseDouble(amount);
            Log.v("**resposneinme**", convertedValue.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("**resposneinme**", convertedValue.toString());

        return convertedValue;
    }



    public void sendBroadcastReply (View view) {
        Log.d("Debug","Entering sendBroadcastReply");

        String apiResponse = receivedResponse();
        Log.v("**resposneinme**", apiResponse.toString());
        Log.v("**resposneinme**", amount.toString());

        double amountConvert = GetConversionRate(apiResponse,amount);
        Log.v("**resposneinme**", String.valueOf(amountConvert));

        Intent intent = new Intent();
        intent.setAction("com.shubhamvadhera.www.bcreceiver");
        intent.putExtra("CURRENCY", currency);
        intent.putExtra("AMOUNT", String.valueOf(amountConvert));
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
        finish();
    }
}
