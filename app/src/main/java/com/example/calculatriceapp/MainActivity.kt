package com.example.calculatriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EDITTEXT_CONTENT: String = "Edit_content"
    }
    private val TAG = "MainActivity"
    var resultEditText: EditText? = null
    private var oneButton: Button? = null
    private var zeroButton: Button? = null
    private var twoButton: Button? = null
    private var threeButton: Button? = null
    private var fourButton: Button? = null
    private var fiveButton: Button? = null
    private var sixButton: Button? = null
    private var sevenButton: Button? = null
    private var eightButton: Button? = null
    private var addButton: Button? = null
    private var subtractButton: Button? = null
    private var divideButton: Button? = null
    private var multiplyButton: Button? = null
    private var equalButton: Button? = null
    private var moduloButton: Button? = null
    private var dotButton: Button? = null
    private var deleteButton: ImageButton? = null
    private var resetButton: Button? = null
    private var operators: List<Char> = listOf('/', '*', '+', '-', '%')

    var nineButton: Button? = null
    private var result: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultEditText = findViewById(R.id.result)
        oneButton = findViewById(R.id.one_button)
        zeroButton = findViewById(R.id.zero_button)
        twoButton = findViewById(R.id.two_button)
        threeButton = findViewById(R.id.three_button)
        fourButton = findViewById(R.id.four_button)
        fiveButton = findViewById(R.id.five_button)
        sixButton = findViewById(R.id.six_button)
        sevenButton = findViewById(R.id.seven_button)
        eightButton = findViewById(R.id.eight_button)
        nineButton = findViewById(R.id.nine_button)
        addButton = findViewById(R.id.add_button)
        subtractButton = findViewById(R.id.subtract_button)
        divideButton = findViewById(R.id.divide_button)
        multiplyButton = findViewById(R.id.multiply_button)
        equalButton = findViewById(R.id.equal_button)
        dotButton = findViewById(R.id.dot_button)
        deleteButton = findViewById(R.id.delete_button)
        resetButton = findViewById(R.id.reset)
        moduloButton = findViewById(R.id.modulo_button)


        resultEditText?.setOnClickListener(this)
        zeroButton?.setOnClickListener(this)
        oneButton?.setOnClickListener(this)
        twoButton?.setOnClickListener(this)
        threeButton?.setOnClickListener(this)
        fourButton?.setOnClickListener(this)
        fiveButton?.setOnClickListener(this)
        sixButton?.setOnClickListener(this)
        sevenButton?.setOnClickListener(this)
        eightButton?.setOnClickListener(this)
        nineButton?.setOnClickListener(this)
        addButton?.setOnClickListener(this)
        subtractButton?.setOnClickListener(this)
        multiplyButton?.setOnClickListener(this)
        divideButton?.setOnClickListener(this)
        subtractButton?.setOnClickListener(this)
        equalButton?.setOnClickListener(this)
        dotButton?.setOnClickListener(this)
        resetButton?.setOnClickListener(this)
        deleteButton?.setOnClickListener(this)
        moduloButton?.setOnClickListener(this)

        // If we have a saved state then we can restore it now
        if (savedInstanceState != null) {
            result = savedInstanceState.getString(EDITTEXT_CONTENT, "")
        }

        resultEditText?.setText(result)
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.result -> {
                result = ""
                resultEditText?.setText("")
            }

            R.id.zero_button,
            R.id.one_button,
            R.id.two_button,
            R.id.three_button,
            R.id.four_button,
            R.id.five_button,
            R.id.six_button,
            R.id.seven_button,
            R.id.eight_button,
            R.id.nine_button -> {
                result += (findViewById<Button>(v.id)).text
                resultEditText?.setText(result)
            }

            R.id.delete_button -> {
                result = result.dropLast(1)
                resultEditText?.setText(result)
            }

            R.id.reset -> {
                result = ""
                resultEditText?.setText(result)
            }

            R.id.add_button,
            R.id.subtract_button,
            R.id.multiply_button,
            R.id.divide_button,
            R.id.equal_button,
            R.id.modulo_button,
            R.id.dot_button -> {
                val operator: String = (findViewById<Button>(v.id)).text.toString()
                if (result.isEmpty()) return

                val resultLength = result.length - 1
                val lastChar = result[resultLength]

                if (lastChar.isAnOperator()) {
                    if (operator != "=") replaceChar(resultLength, operator[0])
                    else return
                } else {
                    val operatorsInResult = operators.filter { result.contains(it) }

                    if (operatorsInResult.isNotEmpty()) {
                        val operands = result.split(operatorsInResult[0])
                        if (operands.size > 1) {
                            val leftOperand = operands[0].toFloat()
                            val rightOperand = operands[1].toFloat()
                            when (operatorsInResult[0]) {
                                '+' -> result =
                                    (leftOperand + rightOperand).toString()
                                '-' -> result =
                                    (leftOperand - rightOperand).toString()
                                '*' -> result =
                                    (leftOperand * rightOperand).toString()
                                '/' -> {
                                    result = if (rightOperand == 0f)
                                        "Cannot divide by zero"
                                    else
                                        (leftOperand / rightOperand).toString()
                                }
                                '%' -> result =
                                    (leftOperand % rightOperand).toString()
                            }
                            //Remove decimal part of float numbers ending by .0
                            if (result.endsWith(".0")) result = result.dropLast(2)
                            result += if (operator != "=") operator else ""
                            resultEditText?.setText(result)
                        }
                    } else {
                        result += if (operator != "=") operator else ""
                        resultEditText?.setText(result)
                    }
                }
            }
        }
    }

    private fun replaceChar(resultLength: Int, newChar: Char) {
        result = result.replace(result[resultLength], newChar)
        resultEditText?.setText(result)
    }

    private fun Char.isAnOperator(): Boolean {
        return this in operators
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDITTEXT_CONTENT, result)
    }

    //TODO Gérer le signe négatif
}


