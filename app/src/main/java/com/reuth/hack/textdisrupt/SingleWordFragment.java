package com.reuth.hack.textdisrupt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by liorr on 5/4/17.
 */

public class SingleWordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int pageNumber = bundle.getInt(SingleWordActivity.PAGE_NUMBER);

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_single_word, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.scrollViewTextView);
        textView.setText("Hello Lior " + pageNumber);

        return rootView;
    }
}
