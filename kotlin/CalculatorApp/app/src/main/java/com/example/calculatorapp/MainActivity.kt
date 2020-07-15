package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        result.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        result.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot) {
            result.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View) {
        if(lastNumeric && !isOperatorAdded(result.text.toString())) {
            result.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEqual(view: View) {
        if(lastNumeric) {
            var value = result.text.toString()
            var prefix = ""

            try {
                if(value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }

                if(value.contains("-")) {
                    var numbers = value.split("-")
                    result.text = removeZeroAfterDot(((prefix + numbers[0]).toDouble()
                            - numbers[1].toDouble()).toString())
                } else if(value.contains("+")) {
                    var numbers = value.split("+")
                    result.text = removeZeroAfterDot(((prefix + numbers[0]).toDouble()
                            + numbers[1].toDouble()).toString())
                } else if(value.contains("*")) {
                    var numbers = value.split("*")
                    result.text = removeZeroAfterDot(((prefix + numbers[0]).toDouble()
                            * numbers[1].toDouble()).toString())
                } else if(value.contains("/")) {
                    var numbers = value.split("/")
                    result.text = removeZeroAfterDot(((prefix + numbers[0]).toDouble()
                            / numbers[1].toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        if(result.contains(".0"))
            return result.substring(0, result.length - 2)
        return result;
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-"))
            false
        else {
            value.contains("/") || value.contains("*") ||
                    value.contains("+") || value.contains("-")
        }
    }
}