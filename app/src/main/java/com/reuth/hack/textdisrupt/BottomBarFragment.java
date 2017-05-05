package com.reuth.hack.textdisrupt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class BottomBarFragment extends Fragment implements TextToSpeech.OnInitListener {

    //TTS object
    private TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

    public BottomBarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_bar_fragment, container, false);

        createTTS();

        enlargeText(view);
        reduceText(view);
        enlargeSpacing(view);
        reduceSpacing(view);

//        emphPre(view);
//        emphMid(view);
//        emphSuf(view);

        unVowelsText(view);
        paintText(getContext(), view);
        changeFont(view);
        textToSpeech(view);
        addBordersToText(view);


        return view;
    }


    //setup TTS
    @Override
    public void onInit(int initStatus) {

        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.ENGLISH) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.ENGLISH);
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(getActivity(), "Sorry! Text To Speech failed...",
                    Toast.LENGTH_LONG).show();
        }
    }

    //act on result of TTS data check
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(getActivity(), this);
                myTTS.setLanguage(Locale.ENGLISH);
            } else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    private void createTTS() {
        //check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
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
    public void emphText(View mainView) {

        Button b = (Button) mainView.findViewById(R.id.btn_emphasize_middle);
        b.setOnClickListener(new View.OnClickListener() {

            private int last = -1;

            @Override
            public void onClick(View v) {
                final TextView tv = ((TextViewInterface) getActivity()).getTextView();
                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                b.setTitle("Select Empasis:");
                String[] types = {"Prefixes", "Middle Letters", "Suffixes"};
                b.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // call method to clear changes

                        dialog.dismiss();
                        switch (which) {
                            case 0:

                                // wanted to change back - we do nothing
                                if (last == 0) {
                                    break;
                                }

                                // call method to empasis prefixes
                                last = 0;

                                break;
                            case 1:

                                // wanted to change back - we do nothing
                                if (last == 1) {
                                    break;
                                }

                                // call method to empasis middle
                                last = 1;
                            case 2:

                                // wanted to change back - we do nothing
                                if (last == 2) {
                                    break;
                                }

                                // call method to empasis end
                                last = 0;
                        }
                    }
                });

                b.show();
            }
        });


    }



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

    public void textToSpeech(View mainView) {
        Button b = (Button) mainView.findViewById(R.id.btn_text_to_speach);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                myTTS.speak(tv.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, "my_speak");
            }
        });
    }


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
        final Button b = (Button) mainView.findViewById(R.id.btn_unvowels);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                VowelsToggle vowelsToggle = new VowelsToggle(tv.getText().toString());
                String converted = vowelsToggle.removeVowels();
                tv.setText(converted);

                b.setSelected(!b.isSelected());
            }
        });
    }

    public void changeFont(View mainView) {
        Button b = (Button) mainView.findViewById(R.id.btn_change_font);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView tv = ((TextViewInterface) getActivity()).getTextView();
                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                b.setTitle("Select font:");
                String[] types = {"David", "Times New Roman", "Calibri"};
                b.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        switch (which) {
                            case 0:

                                tv.setTypeface(Typeface.createFromAsset(
                                        getActivity().getAssets(), "fonts/David.ttf"));
                                break;
                            case 1:
                                tv.setTypeface(Typeface.createFromAsset(
                                        getActivity().getAssets(), "fonts/TNR.ttf"));
                                break;
                            case 2:
                                tv.setTypeface(Typeface.createFromAsset(
                                        getActivity().getAssets(), "fonts/Calibri.ttf"));
                                break;
                        }
                    }

                });

                b.show();
            }
        });
    }


    public void addBordersToText(View mainView) {
        Button b = (Button) mainView.findViewById(R.id.btn_add_borders);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView tv = ((TextViewInterface) getActivity()).getTextView();
                tv.setBackground(getActivity().getDrawable(R.drawable.back));
            }
        });
    }
}