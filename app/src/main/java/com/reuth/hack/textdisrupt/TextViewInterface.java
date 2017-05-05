package com.reuth.hack.textdisrupt;

import android.text.SpannableString;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shacharla on 5/5/2017.
 */

public interface TextViewInterface {

    TextView getTextView();

    ArrayList<Word> getWordsArray();

    SpannableString getSpanStr();

    int getWordIndex();
}
