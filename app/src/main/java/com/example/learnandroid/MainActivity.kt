package com.example.learnandroid

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toggleSwitch = findViewById<SwitchCompat>(R.id.toggleSwitch)
        val btnTranslate = findViewById<Button>(R.id.translate)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val input = findViewById<TextView>(R.id.editText)

        btnTranslate.setOnClickListener {
            tvResult.text = ""
            val intInput = input.text.toString().toIntOrNull()
            if (intInput != null && intInput in 0..1000) {
                if (toggleSwitch.isChecked) {
                    tvResult.text = engNumber(intInput)
                }
                else {
                    tvResult.text = geoNumber(intInput)
                }
            }
            else {
                tvResult.text = "შეიყვანე სწორი რიცხვი"
            }
        }

    }
}

fun geoTillTwenty(num: Int): String {
    val result = when(num) {
        0 -> "ნული"
        1 -> "ერთი"
        2 -> "ორი"
        3 -> "სამი"
        4 -> "ოთხი"
        5 -> "ხუთი"
        6 -> "ექვსი"
        7 -> "შვიდი"
        8 -> "რვა"
        9 -> "ცხრა"
        10 -> "ათი"
        11 -> "თერთმეტი"
        12 -> "თორმეტი"
        13 -> "ცამეტი"
        14 -> "თოთხმეტი"
        15 -> "თხუთმეტი"
        16 -> "თექვსმეტი"
        17 -> "ჩვიდმეტი"
        18 -> "თვრამეტი"
        19 -> "ცხრამეტი"
        else -> "ოცი"
    }
    return result
}

fun geoTillHundred(num: Int): String {
    val digit1 = num.toString()[0]
    val digit2 = num.toString()[1]
    var result = when(digit1) {
        '2','3' -> "ოცდა"
        '4','5' -> "ორმოცდა"
        '6','7' -> "სამოცდა"
        '8','9' -> "ოთხმოცდა"
        else -> ""
    }

    if (digit1 == '1') {
        result = geoTillTwenty(num) // 20-მდე
    }

    else if (digit2 == '0') {
        when(digit1) {
            '2' -> result = "ოცი"
            '3' -> result = "ოცდაათი"
            '4' -> result = "ორმოცი"
            '5' -> result = "ორმოცდაათი"
            '6' -> result = "სამოცი"
            '7' -> result = "სამოცდაათი"
            '8' -> result = "ოთხმოცი"
            '9' -> result = "ოთხმოცდაათი"
        }
    }

    else if (
        digit1 == '2' || digit1 == '4' || digit1 == '6' || digit1 == '8'
    ) {
        result+=geoTillTwenty(digit2.digitToInt()) // 20s, 40s, 60s, 80s
    }
    else if(
        digit1 == '3' || digit1 == '5' || digit1 == '7' || digit1 == '9'
    ) {
        result+=geoTillTwenty(digit2.digitToInt() + 10) // 30s, 50s, 70s, 90s
    }
    return result

}

fun geoHundreds(num: Int): String {
    val result = when(num) {
        1 -> "ას"
        2 -> "ორას"
        3 -> "სამას"
        4 -> "ოთხას"
        5 -> "ხუთას"
        6 -> "ექვსას"
        7 -> "შვიდას"
        8 -> "რვაას"
        9 -> "ცხრაას"
        else -> ""
    }
    return result
}

fun geoNumber(num: Int): String {
    val numLength = num.toString().length
    var result = ""

    if (numLength == 1) {                   // 1 ციფრა რიცხვი
        result = geoTillTwenty(num.toInt())
    }
    else if (numLength == 2) {              // 2 ციფრა რიცხვი
        result = geoTillHundred(num.toInt())
    }
    else if (numLength == 3) {              // 3 ციფრა რიცხვი
        val digit1 = num.toString()[0]
        val digit2 = num.toString()[1]
        val digit3 = num.toString()[2]
        if (digit1 != '0' && digit2 == '0' && digit3 == '0') {   // 100, 200..900
            result = geoHundreds(digit1.digitToInt()) + "ი"
        }
        else if (digit2 == '0') {           // 101, 204 და ა.შ. როარ გაქრაშოს
            result = geoHundreds(digit1.digitToInt()) + " "
            result += geoTillTwenty(digit3.digitToInt())

        }
        else {
            result = geoHundreds(digit1.digitToInt()) + " "
            val lastTwoDigitNum = (digit2.toString() + digit3.toString()).toInt()
            result += geoTillHundred(lastTwoDigitNum)
        }
    }
    else if (num == 1000) {
        result = "ათასი"
    }
    else {
        result = "შეიყვანეთ რიცხვი 0-1000 მდე"
    }

    return result
}

// =========================================== BONUS ===============================================

fun engTillTwenty(num: Int): String {
    val result = when(num) {
        0 -> "zero"
        1 -> "one"
        2 -> "two"
        3 -> "three"
        4 -> "four"
        5 -> "five"
        6 -> "six"
        7 -> "seven"
        8 -> "eight"
        9 -> "nine"
        10 -> "ten"
        11 -> "eleven"
        12 -> "twelve"
        13 -> "thirteen"
        14 -> "fourteen"
        15 -> "fifteen"
        16 -> "sixteen"
        17 -> "seventeen"
        18 -> "eighteen"
        19 -> "nineteen"
        else -> "twenty"
    }
    return result
}

fun engTillHundred(num: Int): String {
    val digit1 = num.toString()[0]
    val digit2 = num.toString()[1]
    var result = when(digit1) {
        '2' -> "twenty"
        '3' -> "thirty"
        '4' -> "forty"
        '5' -> "fifty"
        '6' -> "sixty"
        '7' -> "seventy"
        '8' -> "eighty"
        '9' -> "ninety"
        else -> ""
    }
    if (digit2 != '0') {
        result+= "-"
        result+= engTillTwenty(digit2.digitToInt())
    }
    if (digit1 == '1') {
        result = engTillTwenty(num) // 20-მდე
    }
    return result
}

fun engNumber(num: Int): String {
    val numLength = num.toString().length
    var result = ""

    if (numLength == 1) {
        result = engTillTwenty(num)
    }
    else if(numLength == 2) {
        result = engTillHundred(num)
    }
    else if(numLength == 3) {
        val digit1 = num.toString()[0]
        val digit2 = num.toString()[1]
        val digit3 = num.toString()[2]
        if (digit1 != '0' && digit2 == '0' && digit3 == '0') {
            result = engTillTwenty(digit1.digitToInt()) + " hundred"
        }
        else if (digit2 == '0') {
            result = engTillTwenty(digit1.digitToInt()) + " hundred "
            result += engTillTwenty(digit3.digitToInt())
        }
        else {
            result = engTillTwenty(digit1.digitToInt()) + " hundred "
            val lastTwoDigitNum = (digit2.toString() + digit3.toString()).toInt()
            result += engTillHundred(lastTwoDigitNum)
        }
    }
    else if (num == 1000) {
        result = "one thousand"
    }
    else {
        result = "შეიყვანეთ რიცხვი 0-1000 მდე"
    }

    return result
}