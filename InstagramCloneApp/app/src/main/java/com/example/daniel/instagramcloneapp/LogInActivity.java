package com.example.daniel.instagramcloneapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class LogInActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {
    EditText logInUsername;
    EditText logInPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInUsername = findViewById(R.id.logInUsername);
        logInPassword = findViewById(R.id.logInPass);
        ConstraintLayout bgConstraintLayout = findViewById(R.id.bgLogInCL);
        ImageView imageView = findViewById(R.id.instaLogo);

        logInPassword.setOnKeyListener(this);
        bgConstraintLayout.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    public void logUser(View view) {
        if(TextUtils.isEmpty(logInUsername.getText().toString())) {
            logInUsername.setError("Enter a username, ffs");
            return;
        }

        if(TextUtils.isEmpty(logInPassword.getText().toString())) {
            logInPassword.setError("Enter a password, ffs");
            return;
        }

        ParseUser.logInInBackground(logInUsername.getText().toString(), logInPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(LogInActivity.this, UserListActivity.class);
                    Toast.makeText(LogInActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(LogInActivity.this, "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    ParseQuery<ParseUser> query = ParseUser.getQuery();
    public void signUpButton(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);

        if(!TextUtils.isEmpty(logInUsername.getText().toString())) {
            intent.putExtra("username", logInUsername.getText().toString());
        } else if(!TextUtils.isEmpty(logInPassword.getText().toString())) {
            intent.putExtra("password", logInPassword.getText().toString());
        }

        startActivity(intent);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN  ) {
            logUser(view);
            return true;
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        //close keyboard when touched outside the keyboard
        if(view.getId() == R.id.bgLogInCL || view.getId() == R.id.instaLogo) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }
}
