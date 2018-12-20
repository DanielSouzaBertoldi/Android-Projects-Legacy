package com.example.daniel.teste;

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
                .applicationId("d3090079da77c1ec79c5d2b68266b440f8ae209f")
                .clientKey("e6c575ea0408db54bef097d41f112b767aa0d7b3")
                .server( "http://18.223.247.146:80/parse/")
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