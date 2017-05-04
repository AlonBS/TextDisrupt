package com.reuth.hack.textdisrupt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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


        enlargeText(view);
        reduceText(view);
        enlargeSpacing(view);
        reduceSpacing(view);

//        emphPre(view);
//        emphMid(view);
//        emphSuf(view);

//        textToSpeech(view);

        unVowelsText(view);
        paintText(getContext(), view);



        return view;
    }

    public void enlargeText(View mainView) {
        Button b = (Button) mainView.findViewById(R.id.btn_change_size_bigger);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                float text_size = tv.getTextSize();
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size + 10);
            }
        });
    }

    public void reduceText(View mainView) {
        Button b = (Button) mainView.findViewById(R.id.btn_change_size_smaller);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                float text_size = tv.getTextSize();
                tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size - 10);
            }
        });
    }

    public void reduceSpacing(View mainView) {
        Button b = (Button) mainView.findViewById(R.id.btn_change_line_spacing_smaller);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                float line_spacing = tv.getLineSpacingExtra();
                tv.setLineSpacing (line_spacing - 10, 1);
            }
        });
    }

    public void enlargeSpacing(View mainView) {
        Button b = (Button) mainView.findViewById(R.id.btn_change_line_spacing_bigger);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                float line_spacing = tv.getLineSpacingExtra();
                tv.setLineSpacing (line_spacing + 10, 1);
            }
        });
    }
//    public void emphPre(View mainView) {
//        Button b = (Button) mainView.findViewById(R.id.btn_change_line_spacing_bigger);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView tv = ((TextViewInterface) getActivity()).getTextView();
//                float line_spacing = tv.getLineSpacingExtra();
//                tv.setLineSpacing (line_spacing + 10, 1);
//            }
//        });
//    }



//    public void emphMid(View mainView) {
//        Button b = (Button) mainView.findViewById(R.id.btn_change_line_spacing_bigger);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView tv = ((TextViewInterface) getActivity()).getTextView();
//                float line_spacing = tv.getLineSpacingExtra();
//                tv.setLineSpacing (line_spacing + 10, 1);
//            }
//        });
//    }

//    public void emphSuf(View mainView) {
//        Button b = (Button) mainView.findViewById(R.id.btn_change_line_spacing_bigger);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView tv = ((TextViewInterface) getActivity()).getTextView();
//                float line_spacing = tv.getLineSpacingExtra();
//                tv.setLineSpacing (line_spacing + 10, 1);
//            }
//        });
//    }
//
//    public void textToSpeech(View mainView) {
//        Button b = (Button) mainView.findViewById(R.id.btn_change_line_spacing_bigger);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView tv = ((TextViewInterface) getActivity()).getTextView();
//                float line_spacing = tv.getLineSpacingExtra();
//                tv.setLineSpacing (line_spacing + 10, 1);
//            }
//        });
//    }


    public void paintText(final Context context, View mainView) {
        Button b = (Button) mainView.findViewById(R.id.btn_finger_paint);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                Intent myIntent = new Intent(context, FingerPaint.class);
                myIntent.putExtra("WORD_TO_BE_PAINTED", tv.getText().toString());
                context.startActivity(myIntent);
            }
        });
    }

    public void unVowelsText(View mainView) {
        Button b = (Button) mainView.findViewById(R.id.btn_unvowels);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                VowelsToggle vowelsToggle = new VowelsToggle(tv.getText().toString());
                String converted = vowelsToggle.removeVowels();
                tv.setText(converted);
            }
        });
    }

//    public void changeFont(View mainView) {
//        Button b = (Button) mainView.findViewById(R.id.btn_);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView tv = ((TextViewInterface) getActivity()).getTextView();
//                AlertDialog.Builder b = new AlertDialog.Builder(this);
//                b.setTitle("Example");
//                String[] types = {"By Zip", "By Category", "12"};
//                b.setItems(types, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        dialog.dismiss();
//                        switch(which){
//                            case 0:
//
//                                tv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/David.ttf"));
//                                break;
//                            case 1:
//                                tv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/TNR.ttf"));
//                                break;
//                            case 2:
//                                tv.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Calibri.ttf"));
//                                break;
//                        }
//                    }
//
//                });
//
//                b.show();
//
//                tv.setBackground(getDrawable(R.drawable.back));
//            }
//        });
//    }
}