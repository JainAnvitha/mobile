package com.example.helpme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , MedicineReminder.OnFragmentInteractionListener,HomePage.OnFragmentInteractionListener {


    public ImageView iview;
    TextView tview;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("Main","started");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HomePage home = new HomePage();

        Intent intent = getIntent();

        if(intent.getExtras()!=null)
        {
            if(intent.getExtras().getInt("id")!=0) {



                int i = intent.getExtras().getInt("id");
                String s = intent.getExtras().getString("text");
                View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

                iview = (ImageView) headerView.findViewById(R.id.nav_imageView);
                tview = (TextView) headerView.findViewById(R.id.nav_tv);
                tview.setText("Hi, " + s);
                iview.setImageResource(i);

                Log.e("main activity", String.valueOf(i));

                SharedPreferences sp = getSharedPreferences("Shpr", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putBoolean("logged", true);
                ed.putString("User", s);
                ed.putInt("imgRes", i);
                ed.commit();


                getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();
            }
            else {
                MedicineReminder rm = new MedicineReminder();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, rm).commit();
                SharedPreferences sp=getSharedPreferences("Shpr", Context.MODE_PRIVATE);
                int i = sp.getInt("imgRes",0);
                String s=sp.getString("User",null);
                View headerView =navigationView.inflateHeaderView(R.layout.nav_header_main);

                Log.e("main activity", String.valueOf(i));

                iview=(ImageView)headerView.findViewById(R.id.nav_imageView) ;
                tview=(TextView) headerView.findViewById(R.id.nav_tv);
                tview.setText("Hi, "+s);
                iview.setImageResource(i);
            }

        }
        else {

            getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();

            SharedPreferences sp=getSharedPreferences("Shpr", Context.MODE_PRIVATE);
            Boolean loggedin=sp.getBoolean("logged",false);

            if(!loggedin)
            {
                NameDialog cdd = new NameDialog(this, getApplicationContext());
                cdd.show();
            }
            else
            {
                int i = sp.getInt("imgRes",0);
                Log.e("main activity", String.valueOf(i));

                String s=sp.getString("User",null);

                Log.e("main activity", s);

                View headerView =navigationView.inflateHeaderView(R.layout.nav_header_main);


                iview=(ImageView)headerView.findViewById(R.id.nav_imageView) ;
                tview=(TextView) headerView.findViewById(R.id.nav_tv);
                tview.setText("Hi, "+s);
                iview.setImageResource(i);

            }
        }



//Necessary to show menu items in navigation pane
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.d("Item:",item.toString());
        int id = item.getItemId();

        if (id == R.id.nav_medicinereminder) {
            Log.e("main activity", String.valueOf(id));

            MedicineReminder rm = new MedicineReminder();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, rm).commit();


        } else if (id == R.id.nav_editname) {
            SharedPreferences sp = getSharedPreferences("Shpr", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putBoolean("logged", false);
            ed.putString("User", null);
            ed.putInt("imgRes", 0);
            ed.commit();
            NameDialog cdd = new NameDialog(this, getApplicationContext());
            cdd.show();
        }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
