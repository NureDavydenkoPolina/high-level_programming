package com.example.pz3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SubtractActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subtract)

        val number1 = findViewById<EditText>(R.id.number1)
        val number2 = findViewById<EditText>(R.id.number2)
        val button = findViewById<Button>(R.id.btnSubtract)
        val result = findViewById<TextView>(R.id.resultText)

        val backButton = findViewById<Button>(R.id.btnBack)

        backButton.setOnClickListener {

            finish()

        }

        button.setOnClickListener {

            val num1 = number1.text.toString().toInt()
            val num2 = number2.text.toString().toInt()

            val difference = num1 - num2

            result.text = "Result: $difference"
        }
    }
}