package com.example.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val btn = findViewById<Button>(R.id.button)
        //val txt = findViewById<TextView>(R.id.textView);
        var count = 0;

        button.setOnClickListener {
            count++
            textView.text = count.toString()
           // Toast.makeText(this@MainActivity, "what", Toast.LENGTH_SHORT)
        }
    }
}