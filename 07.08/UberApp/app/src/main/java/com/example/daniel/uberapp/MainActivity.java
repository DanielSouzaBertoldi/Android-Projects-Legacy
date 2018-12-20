package com.example.daniel.uberapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {
    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sw = findViewById(R.id.switch1);

        getSupportActionBar().hide();

        if(ParseUser.getCurrentUser() == null) {
            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e == null) {
                        Log.i("Info", "Anonymous log in successful!");
                    } else {
                        Log.i("Info", "u can't log anonymously");
                    }
                }
            });
        } else {
            //if they already are signed in
            if(ParseUser.getCurrentUser().get("riderOrDriver") != null) {
                Log.i("Info", "Redirecting as "+ ParseUser.getCurrentUser().get("riderOrDriver"));
            }
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void logIn(View view) {
        if(!sw.isChecked()) {
            Log.i("Logged as: ", "Rider");
            ParseUser.getCurrentUser().put("riderOrDriver", "rider");
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.i("Logged as: ", "Driver");
            ParseUser.getCurrentUser().put("riderOrDriver", "driver");
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Intent intent = new Intent(getApplicationContext(), RequestsActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
