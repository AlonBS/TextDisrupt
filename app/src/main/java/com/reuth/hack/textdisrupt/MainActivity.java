package com.reuth.hack.textdisrupt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Layout;
import android.view.MotionEvent;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button mikaButton = (Button) findViewById(R.id.mikaButton);
        mikaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mika(v);
            }
        });

        Comparator<Word> c = new Comparator<Word>() {
            public int compare(Word w1, Word w2) {

                if ((w2.getFirst() >= w1.getFirst()) && (w2.getSecond() <= w1.getSecond())) {
                    return 0;
                }

                if (w1.getFirst() < w2.getFirst()) {
                    return 1;
                }

                return -1;
            }
        };


        alon();
//        Button alonButton = (Button) findViewById(R.id.alonButton);
//        alonButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alon(v);
//            }
//        });

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

    private void mika(View v) {
        Snackbar.make(v, "Mika", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

//    private void alon(View v) {
//        Snackbar.make(v, "Mika", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
//    }

    private void alon() {

        TextView tv = (TextView) findViewById(R.id.main_text_view);

        tv.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Layout layout = ((TextView)v).getLayout();
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (layout != null) {

                    int line = layout.getLineForVertical(y);
                    int offset = layout.getOffsetForHorizontal(line, x);

                    String offsetStr =  String.valueOf(offset);

                    Snackbar.make(v, offsetStr, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

                return false;
            }
        });

    }

    private int getWordIndex(int offset) {


        return -1;
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

        if (id == R.id.nav_change_font) {
            // Handle the camera action
        } else if (id == R.id.nav_change_size_smaller) {

        } else if (id == R.id.nav_change_size_bigger) {

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
