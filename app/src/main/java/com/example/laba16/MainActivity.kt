package com.example.laba16

import android.os.Bundle
import android.graphics.Color
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var currentInput = "" // Для хранения текущего ввода
    private var firstOperand = 0.0 // Первый операнд
    private var operator: String? = null // Оператор

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linearLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        val display = TextView(this).apply {
            text = "0"
            textSize = 24f
            setTextColor(Color.BLACK)
            setBackgroundColor(Color.WHITE)
            setPadding(20, 20, 20, 20)
        }
        linearLayout.addView(display)

        val gridLayout = GridLayout(this).apply {
            rowCount = 4
            columnCount = 4
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val buttonLabels = arrayOf(
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        )

        for (label in buttonLabels) {
            val button = Button(this).apply {
                text = label
                setBackgroundColor(Color.LTGRAY)
                setTextColor(Color.BLACK)
                textSize = 18f
                setOnClickListener { handleButtonPress(label, display) }
            }
            gridLayout.addView(button)
        }

        linearLayout.addView(gridLayout)

        setContentView(linearLayout)
    }

    private fun handleButtonPress(label: String, display: TextView) {
        when (label) {
            in "0".."9" -> { // Если нажата цифра
                currentInput += label
                display.text = currentInput
            }
            "+" -> { // Если нажато сложение
                operator = "+"
                firstOperand = currentInput.toDoubleOrNull() ?: 0.0
                currentInput = ""
            }
            "=" -> { // Если нажато равно
                val secondOperand = currentInput.toDoubleOrNull() ?: 0.0
                val result = when (operator) {
                    "+" -> firstOperand + secondOperand
                    else -> 0.0
                }
                display.text = result.toString()
                currentInput = result.toString()
                operator = null
            }
            "C" -> { // Очистка
                currentInput = ""
                firstOperand = 0.0
                operator = null
                display.text = "0"
            }
        }
    }
}
