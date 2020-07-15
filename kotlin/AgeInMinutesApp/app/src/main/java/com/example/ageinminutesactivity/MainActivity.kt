package com.example.ageinminutesactivity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {view ->
            clickDatePicker(view)
        }
    }

    private fun clickDatePicker(view: View) {
        val myCalendar = Calendar.getInstance()
        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener {
                view, selectedYear, selectedMonth, selectedDayOfMonth ->
                userDateTextView.text = String.format("%02d/%02d/%d", selectedDayOfMonth,
                    selectedMonth, selectedYear)

                // Calculating time diffence
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse("$selectedDayOfMonth/$selectedMonth/$selectedYear")
                val selectedDateInMin = theDate!!.time / 60000 // Could throw an error
                val currDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currDateInMin = currDate!!.time / 60000
                val diffInMin = currDateInMin - selectedDateInMin
                val currDateIndDays = (abs(theDate.time - currDate.time)) / (24 * 60 * 60 * 1000)
                userMinutesTextView.text = diffInMin.toString()
                userDaysTextView.text = currDateIndDays.toString()
            },
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH) + 1,
            myCalendar.get(Calendar.DAY_OF_MONTH))
        dpd.datePicker.maxDate = Date().time - (1000 * 60 * 60 * 24) // Milliseconds of one day
        dpd.show()
    }
}