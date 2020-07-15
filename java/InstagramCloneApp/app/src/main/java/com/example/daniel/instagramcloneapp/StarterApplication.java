package com.example.daniel.instagramcloneapp;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("1e696993f1d7abf0f80ec62c8d30e2532d4d31c8")
                .clientKey("e47f0b0c23e633175f5be016fcf9b0b090c81070")
                .server("http://18.191.161.160:80/parse/")
                .build()
        );

        //ParseUser.enableAutomaticUser(); //not needed if you're sign in/up yourself, this created the first row in Parse
                                           //when inserting a "valid" user, it will overwrite the automaticUser
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}