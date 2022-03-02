package dev.acuon.calculator

import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val ADDITION = '+'
    private val SUBTRACTION = '-'
    private val MULTIPLICATION = '*'
    private val DIVISION = '/'
    private val EQU = '='
    private val EXTRA = '@'
    private val MODULUS = '%'
    private var ACTION = 0.toChar()
    private var val1 = Double.NaN
    private var val2 = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            input!!.text = input!!.text.toString() + "1"
        }
        button2!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            input!!.text = input!!.text.toString() + "2"
        }
        button3!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            input!!.text = input!!.text.toString() + "3"
        }
        button4!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            input!!.text = input!!.text.toString() + "4"
        }
        button5!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            input!!.text = input!!.text.toString() + "5"
        }
        button6!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            input!!.text = input!!.text.toString() + "6"
        }
        button7!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            input!!.text = input!!.text.toString() + "7"
        }
        button8!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            input!!.text = input!!.text.toString() + "8"
        }
        button9!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            input!!.text = input!!.text.toString() + "9"
        }
        button0!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            input!!.text = input!!.text.toString() + "0"
        }
        button00!!.setOnClickListener {
            ifErrorOnOutput()
            exceedLength()
            if (input.text.toString().length == 1) {
                if (input.text.toString() != "0") {
                    input!!.text = input!!.text.toString() + "00"
                }
            } else if (input.text.toString().length > 0) {
                input!!.text = input!!.text.toString() + "00"
            } else {
                input!!.text = input!!.text.toString() + "0"
            }
        }
        button_dot!!.setOnClickListener {
            exceedLength()
            if (input.text.toString().length > 0 && !containsDot()) {
                input!!.text = input!!.text.toString() + "."
            }
        }
        button_percentage!!.setOnClickListener {
            if (input!!.text.length > 0) {
                ACTION = MODULUS
                operation()
                if (!ifReallyDecimal()) {
                    output!!.text = "$val1%"
                } else {
                    output!!.text = "$val1%"
                }
                input!!.text = null
            } else {
                output!!.text = "Error"
            }
        }
        button_add!!.setOnClickListener {
            if (input!!.text.length > 0) {
                ACTION = ADDITION
                operation()
                if (!ifReallyDecimal()) {
                    output!!.text = "$val1+"
                } else {
                    output!!.text = "$val1+"
                }
                input!!.text = null
            } else {
                output!!.text = "Error"
            }
        }
        button_sub!!.setOnClickListener {
            if (input!!.text.length > 0) {
                ACTION = SUBTRACTION
                operation()
                if (input!!.text.length > 0)
                    if (!ifReallyDecimal()) {
                        output!!.text = "$val1-"
                    } else {
                        output!!.text = "$val1-"
                    }
                input!!.text = null
            } else {
                output!!.text = "Error"
            }
        }
        button_multi!!.setOnClickListener {
            if (input!!.text.length > 0) {
                ACTION = MULTIPLICATION
                operation()
                if (!ifReallyDecimal()) {
                    output!!.text = "$val1×"
                } else {
                    output!!.text = "$val1×"
                }
                input!!.text = null
            } else {
                output!!.text = "Error"
            }
        }
        button_divide!!.setOnClickListener {
            if (input!!.text.length > 0) {
                ACTION = DIVISION
                operation()
                if (ifReallyDecimal()) {
                    output!!.text = "$val1/"
                } else {
                    output!!.text = "$val1/"
                }
                input!!.text = null
            } else {
                output!!.text = "Error"
            }
        }
        button_para2!!.setOnClickListener {
            if (!output!!.text.toString().isEmpty() || !input!!.text.toString().isEmpty()) {
                val1 = input!!.text.toString().toDouble()
                ACTION = EXTRA
                output!!.text = "-" + input!!.text.toString()
                input!!.text = ""
            } else {
                output!!.text = "Error"
            }
        }
        button_equal!!.setOnClickListener {
            if (input!!.text.length > 0) {
                operation()
                ACTION = EQU
                if (!ifReallyDecimal()) {
                    output!!.text = val1.toString()
                } else {
                    output!!.text = "$val1"
                }
                input!!.text = null
            } else {
                output!!.text = "Error"
            }
        }
        button_clear!!.setOnClickListener {
            if (input!!.text.length > 0) {
                val name: CharSequence = input!!.text.toString()
                input!!.text = name.subSequence(0, name.length - 1)
            } else {
                val1 = Double.NaN
                val2 = Double.NaN
                input!!.text = ""
                output!!.text = ""
            }
        }

        // Empty text views on long click.
        button_clear!!.setOnLongClickListener {
            val1 = Double.NaN
            val2 = Double.NaN
            input!!.text = ""
            output!!.text = ""
            true
        }
    }

    private fun operation() {
        if (!java.lang.Double.isNaN(val1)) {
            if (output!!.text.toString()[0] == '-') {
                val1 *= -1
            }
            val2 = input!!.text.toString().toDouble()
            when (ACTION) {
                ADDITION -> val1 += val2
                SUBTRACTION -> val1 -= val2
                MULTIPLICATION -> val1 *= val2
                DIVISION -> val1 /= val2
                EXTRA -> val1 *= -1
                MODULUS -> val1 %= val2
                EQU -> {}
            }
        } else {
            val1 = input!!.text.toString().toDouble()
        }
    }

    // Remove error message that is already written there.
    private fun ifErrorOnOutput() {
        if (output!!.text.toString() == "Error") {
            output!!.text = ""
        }
    }

    // Whether value if a double or not
    private fun ifReallyDecimal(): Boolean {
        return val1 == val1.toDouble()
    }

    // Whether the number contains the dot or not
    private fun containsDot(): Boolean {
        if(input.text.toString().contains(".")) return true
        return false
    }

    private fun noOperation() {
        var inputExpression = output!!.text.toString()
        if (!inputExpression.isEmpty() && inputExpression != "Error") {
            if (inputExpression.contains("-")) {
                inputExpression = inputExpression.replace("-", "")
                output!!.text = ""
                val1 = inputExpression.toDouble()
            }
            if (inputExpression.contains("+")) {
                inputExpression = inputExpression.replace("+", "")
                output!!.text = ""
                val1 = inputExpression.toDouble()
            }
            if (inputExpression.contains("/")) {
                inputExpression = inputExpression.replace("/", "")
                output!!.text = ""
                val1 = inputExpression.toDouble()
            }
            if (inputExpression.contains("%")) {
                inputExpression = inputExpression.replace("%", "")
                output!!.text = ""
                val1 = inputExpression.toDouble()
            }
            if (inputExpression.contains("×")) {
                inputExpression = inputExpression.replace("×", "")
                output!!.text = ""
                val1 = inputExpression.toDouble()
            }
        }
    }

    // Make text small if too many digits.
    private fun exceedLength() {
        if (input!!.text.toString().length > 10) {
            input!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        }
    }
}