package com.reuth.hack.textdisrupt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class BottomBarFragment extends Fragment implements TextToSpeech.OnInitListener {

    //TTS object
    private TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;
    float dX, dY;
    private int lastEmphChoice = -1;
    private VowelsToggle vowelsToggle = null;

    public BottomBarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_bar_fragment, container, false);

        createTTS();

        // on screen
        enlargeText(view);
        reduceText(view);
        enlargeSpacing(view);
        reduceSpacing(view);
        paintText(getContext(), view);
        textToSpeech(view);
        emphText(view);
        // extra options
        setExtraOptions(view);


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
        ImageButton b = (ImageButton) mainView.findViewById(R.id.btn_change_size_bigger);
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
        ImageButton b = (ImageButton) mainView.findViewById(R.id.btn_change_size_smaller);
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
        ImageButton b = (ImageButton) mainView.findViewById(R.id.btn_change_line_spacing_smaller);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                float line_spacing = tv.getLineSpacingExtra();
                tv.setLineSpacing(Math.max(line_spacing - 10, 0), 1);
            }
        });
    }


    public void enlargeSpacing(View mainView) {
        ImageButton b = (ImageButton) mainView.findViewById(R.id.btn_change_line_spacing_bigger);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                float line_spacing = tv.getLineSpacingExtra();
                tv.setLineSpacing(Math.min(line_spacing + 10, 100), 1);
            }
        });
    }


    public void paintText(final Context context, View mainView) {
        ImageButton b = (ImageButton) mainView.findViewById(R.id.btn_finger_paint);
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


    public void textToSpeech(View mainView) {
        final ImageButton b = (ImageButton) mainView.findViewById(R.id.btn_text_to_speach);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = ((TextViewInterface) getActivity()).getTextView();
                myTTS.speak(tv.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, "my_speak");

            }
        });
    }

    public void emphText(View mainView) {
        ImageButton b = (ImageButton) mainView.findViewById(R.id.btn_emphasize_text);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                b.setTitle("Select Emphasis:");
                String[] types = {"Prefixes", "Middle Letters", "Suffixes"};
                b.setItems(types, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        emphCurrentText(dialog, which, true);
                    }
                });

                b.show();

            }
        });
    }

    private void eraseCurrentEmph(TextView tv, SpannableString span_str) {
//        Toast.makeText(getActivity(), tv.getText(),
//                Toast.LENGTH_LONG).show();
        SpannableString ss = (SpannableString) tv.getText();
        ForegroundColorSpan[] spans = ss.getSpans(0, tv.getText().length(),
                ForegroundColorSpan.class);
        int spans_length = spans.length;
        if (spans_length > 0) {
            for (ForegroundColorSpan span : spans) {
                span_str.removeSpan(span);
            }
        }
    }

    private void emphCurrentText(DialogInterface dialog, int which, boolean isEmphTextFlow) {
        final TextView tv = ((TextViewInterface) getActivity()).getTextView();
        final SpannableString span_str = ((TextViewInterface) getActivity()).getSpanStr();
        final ArrayList<Word> words_array = (
                (TextViewInterface) getActivity()).getWordsArray();
        int text_color = Color.RED;
        int wordIndex = ((TextViewInterface) getActivity()).getWordIndex();
        eraseCurrentEmph(tv, span_str);

        if (isEmphTextFlow && dialog != null) {
            dialog.dismiss();
        }

        switch (which) {
            case 0:

                // wanted to change back - we do nothing
                if (isEmphTextFlow && lastEmphChoice == 0) {
                    lastEmphChoice = -1;
                    break;
                }

                // call method to empasis prefixes
                if (wordIndex == -1) {
                    for (Word w : words_array) {
                        int beginIndex = w.getBegin();
                        int endIndex = Math.min(beginIndex + 2, w.getEnd());
                        span_str.setSpan(new ForegroundColorSpan(text_color),
                                beginIndex, endIndex, 0);
                    }
                } else {
                    Word w = words_array.get(wordIndex);
                    int beginIndex = w.getBegin();
                    int endIndex = Math.min(beginIndex + 2, w.getEnd());
                    span_str.setSpan(new ForegroundColorSpan(text_color),
                            beginIndex, endIndex, 0);
                }

                lastEmphChoice = 0;
                break;
            case 1:

                // wanted to change back - we do nothing
                if (isEmphTextFlow && lastEmphChoice == 1) {
                    lastEmphChoice = -1;
                    break;
                }

                // call method to empasis middle
                if (wordIndex == -1) {
                    for (Word w : words_array) {
                        int beginIndex = w.getBegin() + 2;
                        int endIndex = w.getEnd() - 2;
                        if (beginIndex < endIndex) {
                            span_str.setSpan(new ForegroundColorSpan(text_color),
                                    beginIndex, endIndex, 0);
                        }
                    }
                } else {
                    Word w = words_array.get(wordIndex);
                    int beginIndex = w.getBegin() + 2;
                    int endIndex = w.getEnd() - 2;
                    if (beginIndex < endIndex) {
                        span_str.setSpan(new ForegroundColorSpan(text_color),
                                beginIndex, endIndex, 0);
                    }
                }


                lastEmphChoice = 1;
                break;

            case 2:

                // wanted to change back - we do nothing
                if (isEmphTextFlow && lastEmphChoice == 2) {
                    lastEmphChoice = -1;
                    break;
                }

                // call method to empasis end
                if (wordIndex == -1) {
                    for (Word w : words_array) {
                        int endIndex = w.getEnd();
                        int beginIndex = Math.max(w.getBegin(), endIndex - 2);
                        span_str.setSpan(new ForegroundColorSpan(text_color),
                                beginIndex, endIndex, 0);
                    }
                } else {
                    Word w = words_array.get(wordIndex);
                    int endIndex = w.getEnd();
                    int beginIndex = Math.max(w.getBegin(), endIndex - 2);
                    span_str.setSpan(new ForegroundColorSpan(text_color),
                            beginIndex, endIndex, 0);
                }


                lastEmphChoice = 2;
                break;
        }
        tv.setText(span_str);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public void setExtraOptions(final View mainView) {

//        changeFont(view);
//        emphText(view);
//        addBordersToText(view);
//        unVowelsText(view);
//        moveAround(view);

        final ImageButton ib = (ImageButton) mainView.findViewById(R.id.btn_extra_options);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView tv = ((TextViewInterface) getActivity()).getTextView();
                final AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
                b.setTitle("Select option:");
                String[] types = {"שינוי פונט", "תחימת טקסט",
                        "הוספה / הסרה של ניקוד", "תזוזה חופשית"};

                b.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        switch (which) {
                            case 0:

                                changeFont(mainView);
                                break;
                            case 1:
                                addBordersToText(mainView, ib);
                                break;
                            case 2:
                                unVowelsText(mainView, ib);
                                break;
                            case 3:
                                moveAround(mainView);
                                break;
                        }
                    }

                });

                b.show();
            }
        });

    }


    public void changeFont(View mainView) {

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


    public void addBordersToText(View mainView, ImageButton ib) {

        final TextView tv = ((TextViewInterface) getActivity()).getTextView();

        if (!ib.isSelected()) {
            tv.setBackground(getActivity().getDrawable(R.drawable.back));
        } else {
            tv.setBackground(null);
        }

        ib.setSelected(!ib.isSelected());

    }

    public void unVowelsText(View mainView, ImageButton ib) {

        TextView tv = ((TextViewInterface) getActivity()).getTextView();

        if (!ib.isSelected()) {
            vowelsToggle = new VowelsToggle(tv.getText().toString());
            tv.setText(vowelsToggle.removeVowels());
        } else {
            tv.setText(vowelsToggle.getOriginalString());
        }

        ib.setSelected(!ib.isSelected());

//        emphCurrentText(null, lastEmphChoice, false);
    }

    public void moveAround(View mainView) {
//        Button b = (Button) mainView.findViewById(R.id.btn_move_around);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
        TextView tv = ((TextViewInterface) getActivity()).getTextView();
        if (getActivity().getClass().getCanonicalName().equals(MainActivity.class)) {
            return;
        }
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        v.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
//            }
//        });
    }

}