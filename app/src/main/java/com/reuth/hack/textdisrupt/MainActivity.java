package com.reuth.hack.textdisrupt;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnInitListener {

    //TTS object
    private TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTTS();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button mikaButton = (Button) findViewById(R.id.mikaButton);
        mikaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mika(v);
            }
        });

        Button alonButton = (Button) findViewById(R.id.alonButton);
        alonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alon(v);
            }
        });

        Button liorButton = (Button) findViewById(R.id.liorButton);
        liorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lior(v);
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //setup TTS
    public void onInit(int initStatus) {

        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {

            if(myTTS.isLanguageAvailable(Locale.ENGLISH)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.ENGLISH);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

    private void mika(View v) {
        myTTS.speak("hello world", TextToSpeech.QUEUE_FLUSH, null, "my_speak");
    }

    private void createTTS(){
        //check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this, this);
            } else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    private void alon(View v) {
        Snackbar.make(v, "Mika", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void lior(View v) {
        Intent myIntent = new Intent(this, SingleWordActivity.class);
        this.startActivity(myIntent);
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
        int id = item.getItemId();
        TextView main_text = (TextView) findViewById(R.id.main_text_view);
        if (id == R.id.nav_change_font) {
            // Handle the camera action
        } else if (id == R.id.nav_change_size_smaller) {
            float text_size = main_text.getTextSize();
//            Toast.makeText(this, Float.toString(text_size), Toast.LENGTH_LONG).show();
            main_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size - 10);

        } else if (id == R.id.nav_change_size_bigger) {
            float text_size = main_text.getTextSize();
//                     Toast.makeText(this, Float.toString(text_size), Toast.LENGTH_LONG).show();
            main_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size + 10);

        } else if (id == R.id.nav_change_line_spacing_smaller) {
            float line_spacing = main_text.getLineSpacingExtra();
            main_text.setLineSpacing (line_spacing - 10, 1);
        } else if (id == R.id.nav_change_line_spacing_bigger) {
            float line_spacing = main_text.getLineSpacingExtra();
            main_text.setLineSpacing (line_spacing + 10, 1);
        } else if (id == R.id.nav_emphasize_prefix) {

        } else if (id == R.id.nav_emphasize_middle) {

        } else if (id == R.id.nav_emphasize_suffix) {

        } else if (id == R.id.nav_emphasize_margin) {

        } else if (id == R.id.nav_text_to_speach) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
