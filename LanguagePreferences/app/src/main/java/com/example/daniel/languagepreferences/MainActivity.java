package com.example.daniel.languagepreferences;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView textView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.daniel.languagepreferences", Context.MODE_PRIVATE);
        textView = (TextView) findViewById(R.id.textView);

        String language = sharedPreferences.getString("language", "");

        if (language == "") {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_map)
                    .setTitle("Select a language")
                    .setMessage("What language do you like?")
                    .setNeutralButton("Spanish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setLanguage("English");
                        }
                    })
                    .setNeutralButton("English", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setLanguage("Spanish");
                        }
                    })
                    .setCancelable(true)
                    .show();
        } else {
            textView.setText(language);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case R.id.english:
                setLanguage("English");
                return true;
            case R.id.spanish:
                setLanguage("Spanish");
                return true;
            default:
                return false;
        }
    }

    public void setLanguage(String language) {
        sharedPreferences.edit().putString("language", language).apply();

        textView.setText(language);
    }
}
