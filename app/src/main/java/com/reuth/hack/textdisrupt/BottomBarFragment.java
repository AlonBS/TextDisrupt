package com.reuth.hack.textdisrupt;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class BottomBarFragment extends Fragment {

    public BottomBarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_bottom_bar_fragment, container, false);

        enlargeText(getContext(), view, "some text as param");

        return view;
    }

    public static void enlargeText(final Context context, View mainView, final String str) {
        Button b = (Button) mainView.findViewById(R.id.btn_change_size_bigger);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - Shachar
                Toast.makeText(context, str, Toast.LENGTH_LONG).show();
            }
        });
    }
}