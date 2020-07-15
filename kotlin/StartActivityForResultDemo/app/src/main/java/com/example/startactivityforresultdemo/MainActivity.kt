package com.example.startactivityforresultdemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val FIRST_ACTIVITY_REQUEST_CODE = 1
        private const val SECOND_ACTIVITY_REQUEST_CODE = 2

        const val NAME = "name"
        const val EMAIL = "email"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFirstActivity.setOnClickListener {
            startActivityForResult(Intent(this, FirstActivity::class.java),
                FIRST_ACTIVITY_REQUEST_CODE)
        }

        btnSecondActivity.setOnClickListener {
            startActivityForResult(Intent(this, SecondActivity::class.java),
            SECOND_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == FIRST_ACTIVITY_REQUEST_CODE)
                first_activity_result.text = "First Activity Result Success"
            if(requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
                if(data!=null) {
                    val name = data.getStringExtra(NAME)
                    val email = data.getStringExtra(EMAIL)
                    second_activity_result.text = "$name / $email"
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("Cancelled", "true")
            Toast.makeText(this@MainActivity, "Result cancelled",
                Toast.LENGTH_SHORT).show()
        }
    }
}