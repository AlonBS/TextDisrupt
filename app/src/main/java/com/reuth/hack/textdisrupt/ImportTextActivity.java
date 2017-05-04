package com.reuth.hack.textdisrupt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ImportTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_text);


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
