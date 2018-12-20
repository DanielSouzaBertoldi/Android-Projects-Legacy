package com.example.daniel.notesdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditor extends AppCompatActivity {

    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID", -1);

        if(noteID != -1) {
            editText.setText(MainActivity.notes.get(noteID));
            editText.setSelection(editText.getText().length());
        } else {
            MainActivity.notes.add(""); //creating empty note
            noteID = MainActivity.notes.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.notes.set(noteID, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sp = getApplicationContext().getSharedPreferences("com.example.daniel.notesdemo", Context.MODE_PRIVATE);
                HashSet<String> hashSet = new HashSet<>(MainActivity.notes);
                sp.edit().putStringSet("notes", hashSet).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
