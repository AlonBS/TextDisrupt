package com.reuth.hack.textdisrupt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.reuth.hack.textdisrupt.SingleWordActivity.WORD_TO_DISPLAY;

/**
 * Created by liorr on 5/4/17.
 */

public class SingleWordFragment extends Fragment {

    private TextView mTextView;
    private String presentedWord = null;

    public static SingleWordFragment getInstance() {
        return new SingleWordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = getArguments();
        View rootView = inflater.inflate(R.layout.fragment_single_word, container, false);

        mTextView = (TextView) rootView.findViewById(R.id.scrollViewTextView);
        mTextView.setText(bundle.getString(WORD_TO_DISPLAY));
        presentedWord = bundle.getString(WORD_TO_DISPLAY);

        return rootView;
    }

    public TextView getTextView(){
        return mTextView;
    }
}
