package com.example.daniel.instagramcloneapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //first, we check to see if the user is already logged in.
        if (ParseUser.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, UserListActivity.class);
            startActivity(intent);

            /*go to homescreen
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);*/
        }
    }
}

//tutorial stuff
        /*setting up parseobject, adding username and score and saving in background while checking if it's been successful or not
        ParseObject score = new ParseObject("Score");
        score.put("username", "DanielSoul");
        score.put("score", 10000);
        score.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Save in bg?", "Yep!");
                } else {
                    Log.i("Save in bg?", "Nope :(. Error: "+e.toString());
                }
            }
        });*/

        /*bring back data from Parse server
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
        query.getInBackground("1LOkzkMW3R", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                object.put("score", 20000);
                object.saveInBackground();

                if (e == null && object != null) {
                    Log.i("Object Value", object.getString("username"));
                    Log.i("Object Value", Integer.toString(object.getInt("score")));
                } else {
                    Log.i("Object Value", "null :(. Error: "+e.toString());
                }
            }
        });*/

        /*Create tweet class, containing username and tweet, then query it, and update the tweet content
        ParseObject tweet = new ParseObject("Tweet");
        tweet.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Tweet Save", "alright");
                } else {
                    Log.i("Tweet Save", "not alright, "+e.toString());
                }
            }
        });

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
        query.getInBackground("Ip3vodSFgC", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                object.put("tweet", "carai errei kkkk iae tweaker");
                object.saveInBackground();

                if(e == null & object != null) {
                    Log.i("Tweet Info", object.getString("username"));
                    Log.i("Tweet Info", object.getString("tweet"));
                } else {
                    Log.i("Tweet Info", "deu ruim "+e.toString());
                }
            }
        });*/

        /* ADVANCED QUERIES
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Score");

        //query.whereEqualTo("username", "XXXTENTACION").setLimit(1);
        query.whereGreaterThan("score", 900);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    Log.i("Number of Objects ", Integer.toString(objects.size()));
                    if(objects.size() > 0) {
                        for(ParseObject object : objects) {
                            object.put("score", object.getInt("score")+50);
                            object.saveInBackground();
                            Log.i("Object ", Integer.toString(object.getInt("score")));

                        }
                    }
                }
            }
        });*/

//Creating a user
        /*ParseUser user = new ParseUser();
        user.setUsername("danielbertoldi");
        user.setPassword("123456");
        user.setEmail("danielbertoldi@msn.com");

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Log.i("Sign In", "Successful");
                } else {
                    Log.i("Sign In", "Unsuccessful");
                }
            }
        });*/

        /*loggin in
        ParseUser.logInInBackground("danielbertoldi", "123456", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null) {
                    Log.i("Log In", "Successful");
                } else {
                    Log.i("Log In", "Unsuccessful, check: "+e.toString());
                }
            }
        });*/

        /*checking if user already logged in
        if(ParseUser.getCurrentUser() != null){
            //means the user is logged in
            Log.i("currentUser", "User logged in: "+ ParseUser.getCurrentUser().getUsername());
        } else {
            //take a wild guess motherfucker
            Log.i("currentUser","User not logged in");
        }

        //logout
        ParseUser.logOut();*/

//ParseAnalytics.trackAppOpenedInBackground(getIntent());