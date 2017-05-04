package com.reuth.hack.textdisrupt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ImportTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_text);

        setArticlesSpinner();
        setNextButtonOnClickListener();

    }


    private void setArticlesSpinner() {

        Spinner staticSpinner = (Spinner) findViewById(R.id.text_options_spinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.Articles,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        staticSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                EditText editText = (EditText) findViewById(R.id.imported_text);
                editText.setText(getResources().getStringArray(R.array.ArticlesData)[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                EditText editText = (EditText) findViewById(R.id.imported_text);
                editText.setText(getResources().getStringArray(R.array.ArticlesData)[0]);
            }
        });


    }

// This will be used if we want to use DB
//    private void setArticlesSpinner() {
//
//        Spinner dynamicSpinner = (Spinner) findViewById(R.id.text_options_spinner);
//
//        String[] items = new String[] { "Chai Latte", "Green Tea", "Black Tea" };
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, items);
//
//        dynamicSpinner.setAdapter(adapter);
//
//        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                Log.v("item", (String) parent.getItemAtPosition(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//            }
//        });
//    }


    private void setNextButtonOnClickListener() {

        Button nextScreenButton = (Button) findViewById(R.id.next_screen_button);
        nextScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = (EditText) findViewById(R.id.imported_text);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("IMPORTED_TEXT", editText.getText().toString());
                startActivity(intent);
            }
        });

    }

}
