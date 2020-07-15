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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {
    EditText signUpUsername;
    EditText signUpEmail;
    EditText signUpPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpUsername = findViewById(R.id.signUpUsername);
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPass = findViewById(R.id.signUpPass);
        ConstraintLayout bgConstraintLayout = findViewById(R.id.bgSignUpCL);
        ImageView imageView = findViewById(R.id.instaLogo);

        signUpPass.setOnKeyListener(this);
        bgConstraintLayout.setOnClickListener(this);
        imageView.setOnClickListener(this);

        Intent intent = getIntent();
        //Checks if something was typed in the log in fields
        if(intent.getExtras() != null && intent.getStringExtra("username") != null) {
            signUpUsername.setText(intent.getStringExtra("username"));
            signUpEmail.requestFocus();
        }

        if(intent.getExtras() != null && intent.getStringExtra("password") != null) {
            signUpPass.setText(intent.getStringExtra("password"));
            //signUpPass.setSelection(signUpPass.getText().length());
        }
    }

    public void signUserUp(View view) {
        ParseUser newUser = new ParseUser();

        if(TextUtils.isEmpty(signUpUsername.getText().toString())) {
            signUpUsername.setError("You forgot the username...");
            return;
        }

        if(TextUtils.isEmpty(signUpEmail.getText().toString())) {
            signUpEmail.setError("You forgot the email..");
            return;
        }

        if(TextUtils.isEmpty(signUpPass.getText().toString())) {
            signUpPass.setError("You forgot the password.");
            return;
        }

        newUser.setUsername(signUpUsername.getText().toString());
        newUser.setEmail(signUpEmail.getText().toString());
        newUser.setPassword(signUpPass.getText().toString());

        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    Intent intent = new Intent(SignUpActivity.this, UserListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void logInButton(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN  ) {
            signUserUp(view);
            return true;
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        //close keyboard when touched outside the keyboard
        if(view.getId() == R.id.bgSignUpCL || view.getId() == R.id.instaLogo) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }
}
