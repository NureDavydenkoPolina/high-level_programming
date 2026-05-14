package com.example.pz3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import kotlin.math.pow
import android.os.Environment

class CalculatorActivity : AppCompatActivity() {

    private var isRomanMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val number1 = findViewById<EditText>(R.id.num1)
        val number2 = findViewById<EditText>(R.id.num2)

        val resultText = findViewById<TextView>(R.id.resultText3)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSubtract = findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDivide = findViewById<Button>(R.id.btnDivide)
        val btnPercent = findViewById<Button>(R.id.btnPercent)
        val btnPower = findViewById<Button>(R.id.btnPower)

        val btnBack = findViewById<Button>(R.id.btnBack)

        val btnNormal = findViewById<Button>(R.id.btnNormal)
        val btnRoman = findViewById<Button>(R.id.btnRoman)

        btnNormal.setOnClickListener {

            isRomanMode = false

            btnPercent.visibility = Button.VISIBLE
            btnPower.visibility = Button.VISIBLE

            number1.hint = "Enter number"
            number2.hint = "Enter number"

        }

        btnRoman.setOnClickListener {

            isRomanMode = true

            btnPercent.visibility = Button.GONE
            btnPower.visibility = Button.GONE

            number1.hint = "Enter Roman number"
            number2.hint = "Enter Roman number"

        }

        fun saveToFile(text: String) {

            val path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            )

            val file = File(path, "history.txt")

            file.appendText(text + "\n")
        }

        btnAdd.setOnClickListener {

            if (isRomanMode) {

                val num1 = romanToInt(number1.text.toString().uppercase())
                val num2 = romanToInt(number2.text.toString().uppercase())

                val result = num1 + num2

                resultText.text = intToRoman(result)
                saveToFile("$num1 + $num2 = ${intToRoman(result)}")

            }
            else {

                val num1 = number1.text.toString().toDouble()
                val num2 = number2.text.toString().toDouble()

                val result = num1 + num2

                resultText.text = result.toString()

                saveToFile("$num1 + $num2 = $result")
            }
        }

        btnSubtract.setOnClickListener {

            if (isRomanMode) {

                val num1 = romanToInt(number1.text.toString().uppercase())
                val num2 = romanToInt(number2.text.toString().uppercase())

                val result = num1 - num2

                resultText.text = intToRoman(result)
                saveToFile("$num1 - $num2 = ${intToRoman(result)}")

            }
            else {

                val num1 = number1.text.toString().toDouble()
                val num2 = number2.text.toString().toDouble()

                val result = num1 - num2

                resultText.text = result.toString()

                saveToFile("$num1 - $num2 = $result")
            }
        }



        btnMultiply.setOnClickListener {

            if (isRomanMode) {

                val num1 = romanToInt(number1.text.toString().uppercase())
                val num2 = romanToInt(number2.text.toString().uppercase())

                val result = num1 * num2

                resultText.text = intToRoman(result)
                saveToFile("$num1 * $num2 = ${intToRoman(result)}")

            }
            else {

                val num1 = number1.text.toString().toDouble()
                val num2 = number2.text.toString().toDouble()

                val result = num1 * num2

                resultText.text = result.toString()

                saveToFile("$num1 * $num2 = $result")
            }
        }



        btnDivide.setOnClickListener {

            if (isRomanMode) {

                val num1 = romanToInt(number1.text.toString().uppercase())
                val num2 = romanToInt(number2.text.toString().uppercase())

                val result = num1 / num2

                resultText.text = intToRoman(result)
                saveToFile("$num1 / $num2 = ${intToRoman(result)}")

            }
            else {

                val num1 = number1.text.toString().toDouble()
                val num2 = number2.text.toString().toDouble()

                val result = num1 / num2

                resultText.text = result.toString()

                saveToFile("$num1 / $num2 = $result")
            }
        }

        btnPercent.setOnClickListener {

            val num1 = number1.text.toString().toDouble()
            val num2 = number2.text.toString().toDouble()

            val result = num1 % num2

            resultText.text = result.toString()

            saveToFile("$num1 % $num2 = $result")
        }

        btnPower.setOnClickListener {

            val num1 = number1.text.toString().toDouble()
            val num2 = number2.text.toString().toDouble()

            val result = num1.pow(num2)

            resultText.text = result.toString()

            saveToFile("$num1 ^ $num2 = $result")
        }

        btnBack.setOnClickListener {

            finish()

        }
    }

    fun romanToInt(roman: String): Int {

        val romanValues = mapOf(
            'I' to 1,
            'V' to 5,
            'X' to 10,
            'L' to 50,
            'C' to 100
        )

        var result = 0
        var i = 0

        while (i < roman.length) {

            val current = romanValues[roman[i]] ?: 0

            if (i + 1 < roman.length) {

                val next = romanValues[roman[i + 1]] ?: 0

                if (current < next) {

                    result += next - current
                    i += 2

                }
                else {

                    result += current
                    i++

                }
            }
            else {

                result += current
                i++

            }
        }

        return result
    }

    fun intToRoman(number: Int): String {

        var num = number

        val values = listOf(
            100 to "C",
            90 to "XC",
            50 to "L",
            40 to "XL",
            10 to "X",
            9 to "IX",
            5 to "V",
            4 to "IV",
            1 to "I"
        )

        var result = ""

        for ((value, symbol) in values) {

            while (num >= value) {

                result += symbol
                num -= value

            }
        }

        return result
    }
}