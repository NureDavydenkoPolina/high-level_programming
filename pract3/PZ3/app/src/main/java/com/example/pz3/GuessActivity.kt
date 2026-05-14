package com.example.pz3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class GuessActivity : AppCompatActivity() {

    private var randomNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess)

        randomNumber = Random.nextInt(1, 101)

        val guessInput = findViewById<EditText>(R.id.guessInput)
        val checkButton = findViewById<Button>(R.id.btnCheck)
        val resultText2 = findViewById<TextView>(R.id.resultText2)
        val backButton = findViewById<Button>(R.id.btnBack)

        checkButton.setOnClickListener {

            val userNumber = guessInput.text.toString().toInt()

            if (userNumber < 1 || userNumber > 100) {

                resultText2.text = "Enter number from 1 to 100"

            }
            else if (userNumber > randomNumber) {

                resultText2.text = "Too big"

            }
            else if (userNumber < randomNumber) {

                resultText2.text = "Too small"

            }
            else {

                resultText2.text = "You win!"

            }
        }

        backButton.setOnClickListener {

            finish()

        }
    }
}