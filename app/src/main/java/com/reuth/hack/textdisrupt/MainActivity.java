package com.reuth.hack.textdisrupt;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.MotionEvent;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TextToSpeech.OnInitListener,
        TextViewInterface {

    //TTS object
    private TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

    ArrayList<Word> words_array = new ArrayList<>();
    private int touchedWordIndex = -1;

    SpannableString span_str;

    TextView text_view;

    Boolean shouldEmphBegin = false, shouldEmphEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String displayedText = getIntent().getStringExtra("IMPORTED_TEXT");

        ((TextView) findViewById(R.id.main_text_view)).setText(displayedText);

        init_app();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    public void init_app() {

        createTTS();

        // build words array (will be used by other activities of this app)
        text_view = (TextView) findViewById(R.id.main_text_view);
        String text_str = text_view.getText().toString();

        buildWordsArray(text_str);


        setTextViewOnLongTouchListener();
        setTextViewOnLongClickListener();


    }


    public TextView getTextView() {
        return text_view;
    }

    public void buildWordsArray(String text_str) {

        BreakIterator bi = BreakIterator.getWordInstance(Locale.US);
        bi.setText(text_str);

        int lastIndex = bi.first();
        while (lastIndex != BreakIterator.DONE) {
            int firstIndex = lastIndex;
            lastIndex = bi.next();

            if (lastIndex != BreakIterator.DONE
                    && Character.isLetterOrDigit(
                    text_str.charAt(firstIndex))) {
                String value = text_str.substring(firstIndex, lastIndex);
                words_array.add(new Word(firstIndex, lastIndex, value));
            }
        }

        span_str = new SpannableString(text_str);
        text_view.setText(span_str);
        text_view.setMovementMethod(LinkMovementMethod.getInstance());
    }


    //setup TTS
    public void onInit(int initStatus) {

        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.ENGLISH) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.ENGLISH);
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

    private void createTTS() {
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


    // This is used in order to calulate the current word that has being touched
    private void setTextViewOnLongTouchListener() {

        TextView tv = (TextView) findViewById(R.id.main_text_view);
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                Layout layout = ((TextView) v).getLayout();
                int x = (int) event.getX();
                int y = (int) event.getY();

                if (layout != null) {

                    int line = layout.getLineForVertical(y);
                    int offset = layout.getOffsetForHorizontal(line, x);

                    touchedWordIndex = getWordIndex(offset);

//                    Snackbar.make(v, "touchedWordIndex: " + touchedWordIndex, Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                }

                return false;
            }
        });
    }


    private void setTextViewOnLongClickListener() {

        TextView tv = (TextView) findViewById(R.id.main_text_view);
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("TOUCHED_WORD_ARRAY", words_array);

                Intent intent = new Intent(getBaseContext(), SingleWordActivity.class);
                intent.putExtra("TOUCHED_WORD_INDEX", touchedWordIndex);
                intent.putExtra("TOUCHED_WORD_BUNDLE", bundle);

                startActivity(intent);

                // open some shitty dialog.
                return false;
            }
        });


    }

    private int getWordIndex(int offset) {


//        Comparator<Word> containedComp = new Comparator<Word>() {
//            public int compare(Word w1, Word w2) {
//
//                if ((w2.getBegin() >= w1.getBegin()) && (w2.getEnd() <= w1.getEnd())) {
//                    return 0;
//                }
//
//                if (w1.getBegin() < w2.getBegin()) {
//                    return 1;
//                }
//
//                return -1;
//            }
//        };

        for (int i = 0; i < words_array.size(); i++) {
            Word word = words_array.get(i);

            if (word != null && word.getBegin() <= offset && offset <= word.getEnd()) {
                return i;
            }
        }

//        int index = Collections.binarySearch(words_array, new Word(offset, offset, null), containedComp);


        return -1;
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

            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Example");
            String[] types = {"By Zip", "By Category", "12"};
            b.setItems(types, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    switch (which) {
                        case 0:

                            text_view.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/David.ttf"));
                            break;
                        case 1:
                            text_view.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/TNR.ttf"));
                            break;
                        case 2:
                            text_view.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Calibri.ttf"));
                            break;
                    }
                }

            });

            b.show();

            text_view.setBackground(getDrawable(R.drawable.back));


            // Handle the camera action
        } else if (id == R.id.nav_change_size_smaller) {
            float text_size = text_view.getTextSize();
            text_view.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size - 10);
        } else if (id == R.id.nav_change_size_bigger) {
            float text_size = text_view.getTextSize();
            text_view.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size + 10);
        } else if (id == R.id.nav_change_line_spacing_smaller) {
            float line_spacing = text_view.getLineSpacingExtra();
            text_view.setLineSpacing(line_spacing - 10, 1);
        } else if (id == R.id.nav_change_line_spacing_bigger) {
            float line_spacing = text_view.getLineSpacingExtra();
            text_view.setLineSpacing(line_spacing + 10, 1);
        } else if (id == R.id.nav_emphasize_prefix) {
            boolean emphBegin = shouldEmphBegin;
            for (Word w : this.words_array) {
                int beginIndex = w.getBegin();
                int endIndex = Math.min(beginIndex + 2, w.getEnd());
                int text_color = Color.RED;
                SpannableString ss = (SpannableString) text_view.getText();
                ForegroundColorSpan[] spans = ss.getSpans(beginIndex, endIndex,
                        ForegroundColorSpan.class);
                int spans_length = spans.length;
                if ((spans_length > 0) && emphBegin) {
                    span_str.removeSpan(spans[0]);
                    shouldEmphBegin = false;
                } else {
                    span_str.setSpan(new ForegroundColorSpan(text_color),
                            beginIndex, endIndex, 0);
                    shouldEmphBegin = true;
                }

            }
        } else if (id == R.id.nav_emphasize_middle) {
            for (Word w : this.words_array) {
                int beginIndex = w.getBegin() + 2;
                int endIndex = w.getEnd() - 2;
                if (beginIndex < endIndex) {
                    int text_color = Color.RED;
                    SpannableString ss = (SpannableString) text_view.getText();
                    ForegroundColorSpan[] spans = ss.getSpans(beginIndex, endIndex,
                            ForegroundColorSpan.class);
                    int spans_length = spans.length;
                    if (spans_length > 0) {
                        span_str.removeSpan(spans[0]);
                    } else {
                        span_str.setSpan(new ForegroundColorSpan(text_color),
                                beginIndex, endIndex, 0);
                    }
                }
            }
        } else if (id == R.id.nav_emphasize_suffix) {
            boolean emphEnd = shouldEmphEnd;
            for (Word w : this.words_array) {
                int endIndex = w.getEnd();
                int beginIndex = Math.max(w.getBegin(), endIndex - 2);
                int text_color = Color.RED;
                SpannableString ss = (SpannableString) text_view.getText();
                ForegroundColorSpan[] spans = ss.getSpans(beginIndex, endIndex,
                        ForegroundColorSpan.class);
                int spans_length = spans.length;
                if ((spans_length > 0) && emphEnd) {
                    span_str.removeSpan(spans[0]);
                    shouldEmphEnd = false;
                } else {
                    span_str.setSpan(new ForegroundColorSpan(text_color),
                            beginIndex, endIndex, 0);
                    shouldEmphEnd = true;
                }
            }
        } else if (id == R.id.nav_emphasize_margin) {

        } else if (id == R.id.nav_text_to_speach) {
            myTTS.speak(text_view.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, "my_speak");
        }
        text_view.setText(span_str);
        text_view.setMovementMethod(LinkMovementMethod.getInstance());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
