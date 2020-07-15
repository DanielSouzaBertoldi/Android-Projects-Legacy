package com.example.daniel.uberapp;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("6d97a92a02ad88c0738a0ffac062b987e6d6bee9")
                .clientKey("26b4c20a56832f20153479c155ad8b8a51b52bb8")
                .server( "http://18.223.185.199:80/parse/")
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