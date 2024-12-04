package com.example.learnandroid

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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

        val btnSave = findViewById<Button>(R.id.saveButton)
        val btnClear = findViewById<Button>(R.id.clearButton)
        val email = findViewById<EditText>(R.id.email)
        val username = findViewById<EditText>(R.id.username)
        val firstName = findViewById<EditText>(R.id.firstName)
        val lastName = findViewById<EditText>(R.id.lastName)
        val age = findViewById<EditText>(R.id.age)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        fun clearFields() {
            email.text.clear()
            username.text.clear()
            firstName.text.clear()
            lastName.text.clear()
            age.text.clear()
        }
        // function to hide views for bonus task
        fun setVisibility(boolean: Boolean) {
            if (boolean) {
                email.visibility = View.VISIBLE
                username.visibility = View.VISIBLE
                firstName.visibility = View.VISIBLE
                lastName.visibility = View.VISIBLE
                age.visibility = View.VISIBLE
                btnSave.visibility = View.VISIBLE
                btnClear.visibility = View.VISIBLE
            }
            else {
                email.visibility = View.GONE
                username.visibility = View.GONE
                firstName.visibility = View.GONE
                lastName.visibility = View.GONE
                age.visibility = View.GONE
                btnSave.visibility = View.GONE
                btnClear.visibility = View.GONE
            }
        }

        btnSave.setOnClickListener {
            val emailValue = email.text.toString()
            val usernameValue = username.text.toString()
            val firstNameValue = firstName.text.toString()
            val lastNameValue = lastName.text.toString()
            val ageValue = age.text.toString().toIntOrNull()

            // check if all fields are filled and age >0
            if (
                emailValue.isNotEmpty() &&
                usernameValue.isNotEmpty() &&
                firstNameValue.isNotEmpty() &&
                lastNameValue.isNotEmpty() &&
                ageValue != null && ageValue > 0 &&
                usernameValue.length >= 10 &&
                isValidEmail(emailValue)
                ) {
                tvResult.setTextColor(Color.BLACK)
                tvResult.text = "მონაცემები წარმატებით შეინახა"

                // BONUS TASK

                // hiding previous input fields
                setVisibility(false)
                // creating text views for bonus task
                val tvEmail = TextView(this).apply {
                    text = "Email: $emailValue"
                    textSize = 20f
                }
                val tvUsername = TextView(this).apply {
                    text = "Username: $usernameValue"
                    textSize = 20f
                }
                val tvFullName = TextView(this).apply {
                    text = "Full name: $firstNameValue $lastNameValue"
                    textSize = 20f
                }
                val tvAge = TextView(this).apply {
                    text = "Age: $ageValue"
                    textSize = 20f
                }
                val btnAgain = Button(this).apply {
                    text = "Again"
                }
                val layout = findViewById<ConstraintLayout>(R.id.main)

                // setting parameters for text views
                val paramsEmail = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topToTop = R.id.main
                    startToStart = R.id.main
                    endToEnd = R.id.main
                    topMargin = 200
                }
                tvEmail.layoutParams = paramsEmail

                val paramsUsername = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topToTop = R.id.main
                    startToStart = R.id.main
                    endToEnd = R.id.main
                    topMargin = 400
                }
                tvUsername.layoutParams = paramsUsername

                val paramsFullName = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topToTop = R.id.main
                    startToStart = R.id.main
                    endToEnd = R.id.main
                    topMargin = 600
                }
                tvFullName.layoutParams = paramsFullName

                val paramsAge = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topToTop = R.id.main
                    startToStart = R.id.main
                    endToEnd = R.id.main
                    topMargin = 800
                }
                tvAge.layoutParams = paramsAge

                val paramsBtnAgain = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 400
                    endToEnd = R.id.main
                    startToStart = R.id.main
                    bottomToBottom = R.id.main
                }
                btnAgain.layoutParams = paramsBtnAgain

                // adding new views to ConstraintLayout
                layout.addView(tvEmail)
                layout.addView(tvUsername)
                layout.addView(tvFullName)
                layout.addView(tvAge)
                layout.addView(btnAgain)

                // go back to main view if "Again" is clicked
                btnAgain.setOnClickListener {
                    setVisibility(true)
                    layout.removeView(tvEmail)
                    layout.removeView(tvUsername)
                    layout.removeView(tvFullName)
                    layout.removeView(tvAge)
                    layout.removeView(btnAgain)
                }

            }
            // incorrect input values
            else {
                tvResult.setTextColor(Color.RED)
                tvResult.text = "შეიყვანეთ სწორი მონაცემები"
            }
        }

        // clear input fields if long click is performed
        btnClear.setOnLongClickListener {
            clearFields()
            true
        }

    }

    // only accepting emails which end with acceptable @ and do not contain special characters
    fun isValidEmail(email: String): Boolean {
        val specialChars = listOf('!', '#', '$', '%', '^', '&', '*', '(', ')', '+', '=', '{',
            '}', '[', ']', '|', '\\', ':', ';', '"', '\'' ,'<', '>', '?', '/')

        for (i in email) {
            if (i in specialChars) {
                return false
            }
        }
        if (
            email.endsWith("@gmail.com") ||
            email.endsWith("@mail.ru") ||
            email.endsWith("@yahoo.com") ||
            email.endsWith("@outlook.com") ||
            email.endsWith("@hotmail.com") ||
            email.endsWith("@icloud.com")
        ) {
            return true
        }
        else {
            return false
        }
    }

}
