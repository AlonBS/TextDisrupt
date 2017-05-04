package com.reuth.hack.textdisrupt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class SingleWordActivity extends AppCompatActivity {

    public static final String WORD_TO_DISPLAY = "WORD_TO_DISPLAY";
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private int wordIndex = -1;
    private ArrayList<Word> words_array;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_word);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wordIndex = getIntent().getIntExtra("TOUCHED_WORD_INDEX", -1);

        Bundle bundle = getIntent().getBundleExtra("TOUCHED_WORD_BUNDLE");
        words_array = bundle.getParcelableArrayList("TOUCHED_WORD_ARRAY");

        Toast.makeText(this, String.valueOf(wordIndex), Toast.LENGTH_LONG).show();



        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(wordIndex);
        mPager.setPageTransformer(true, new DepthPageTransformer());
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 SingleWordFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            SingleWordFragment fragment = SingleWordFragment.getInstance();

            Bundle bundle = new Bundle();
            bundle.putString(WORD_TO_DISPLAY, words_array.get(position).getValue());

            String orig = words_array.get(position).getValue();

            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            if (words_array == null) {
                return 0;
            }

            return words_array.size();
        }
    }

}
