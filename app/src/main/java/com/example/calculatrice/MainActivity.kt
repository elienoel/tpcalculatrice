package com.example.calculatrice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculatrice.ui.theme.CalculatriceTheme

class MainActivity : ComponentActivity() {
    private lateinit var tvCalcul: TextView
    private var result = 0
    private var lastOperator: Char? = null
    private var secondNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCalcul = findViewById(R.id.tv_calcul)

        val buttons = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
            R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
            R.id.btn_8, R.id.btn_9
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { appendNumber((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.btn_plus).setOnClickListener { setOperator('+') }
        findViewById<Button>(R.id.btn_minus).setOnClickListener { setOperator('-') }
        findViewById<Button>(R.id.btn_multiply).setOnClickListener { setOperator('×') }
        findViewById<Button>(R.id.btn_divide).setOnClickListener { setOperator('÷') }

        findViewById<Button>(R.id.btn_equals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btn_clear).setOnClickListener { clear() }
    }

    private fun appendNumber(number: String) {
        if (lastOperator != null) {
            secondNumber += number
        } else {
            tvCalcul.text = tvCalcul.text.toString() + number
        }
    }

    private fun setOperator(operator: Char) {
        if (lastOperator == null) {
            result = tvCalcul.text.toString().toInt()
            lastOperator = operator
            tvCalcul.text = "$result $operator "
        } else {
            calculateResult()
            lastOperator = operator
            tvCalcul.text = "$result $operator "
        }
    }

    private fun calculateResult() {
        if (lastOperator != null && secondNumber.isNotEmpty()) {
            val secondNum = secondNumber.toInt()
            result = when (lastOperator) {
                '+' -> result + secondNum
                '-' -> result - secondNum
                '×' -> result * secondNum
                '÷' -> result / secondNum
                else -> result
            }
            tvCalcul.text = result.toString()
            secondNumber = ""
            lastOperator = null
        }
    }

    private fun clear() {
        tvCalcul.text = "0"
        result = 0
        lastOperator = null
        secondNumber = ""
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatriceTheme {
        Greeting("Android")
    }
}