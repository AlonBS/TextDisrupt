package com.reuth.hack.textdisrupt;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
        View view = inflater.inflate(R.layout.fragment_bottom_bar_fragment, container, false);

        unVowelsText(getContext(), view, "~some text as param");
        enlargeText(getContext(), view, "some text as param");
        enlargeText(getContext(), view, "some text as param");
        enlargeText(getContext(), view, "some text as param");
        enlargeText(getContext(), view, "some text as param");
        enlargeText(getContext(), view, "some text as param");
        enlargeText(getContext(), view, "some text as param");
        enlargeText(getContext(), view, "some text as param");
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
    } //btn_unvowels

    public static void unVowelsText(final Context context, View mainView, final String str) {
        final Button b = (Button) mainView.findViewById(R.id.btn_unvowels);
//        b.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                // show interest in events resulting from ACTION_DOWN
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
////                    return true;
//                    b.setBackgroundColor(Color.BLUE);
//                    b.setPressed(true);
//                    return true;
//                }
//
//                // don't handle event unless its ACTION_UP so "doSomething()" only runs once.
//                else if (event.getAction() != MotionEvent.ACTION_UP) {
////                    return false;
//                    b.setBackgroundColor(Color.GRAY);
//                    b.setPressed(false);
//                    return false;
//                } else {
//                    return false;
//                }
//
//
////                doSomething();
////                Toast.makeText(context, "bla bla", Toast.LENGTH_LONG).show();
////                b.setPressed(true);
////                return false;
//            }
//        });

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (!b.isSelected()) {
//                        b.setBackgroundColor(Color.BLUE);
//                    } else {
//                        b.setBackgroundColor(Color.GRAY);
//                    }

                    b.setSelected(!b.isSelected());
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            b.setPressed(!b.isPressed());
//                        }
//                    });
                }
            });


//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO - Shachar
//                Toast.makeText(context, str, Toast.LENGTH_LONG).show();
//
//                v.setPressed(true);
//                b.setPressed(true);
//                b.setSelected(true);
//
//                VowelsToggle vowelsToggle = new VowelsToggle(str);
//                String converted = vowelsToggle.removeVowels();
//
//            }
//        });
    }
}