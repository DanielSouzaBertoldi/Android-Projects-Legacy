package com.example.daniel.databasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase UserActivity = this.openOrCreateDatabase("UserActivity", MODE_PRIVATE, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isTableExists()) {
            UserActivity.execSQL("CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY, username VARCHAR, array VARCHAR)");
            UserActivity.execSQL("INSERT INTO Users (name, array) VALUES ('admin', '100')", null);
            /*Adicionar o resto da glr*/
        }
    }

    public boolean isTableExists() {
        Cursor cursor = UserActivity.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'SADUSAHDUHSAU'", null);

        if(cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
        }

        cursor.close();
        return false;
    }
}
