package com.example.learnandroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learnandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var users: MutableMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        users = mutableMapOf<String, String>()

        binding.addUserButton.setOnClickListener {
            val fullName = binding.fullName.text.toString()
            val email = binding.email.text.toString()

            if (
                fullName.isNotEmpty() && email.isNotEmpty() &&
                isValidEmail(email) && !userExists(email)
                ) {
                // clear warning if it was shown before
                binding.tvWarning.text = ""
                users.put(email, fullName)

                // update user count after adding user
                binding.tvUsersCount.text = "Users -> ${countUsers()}"
            }
            else if(userExists(email)) {
                binding.tvWarning.text = "User already exists"
            }
            else {
                binding.tvWarning.text = "Input correct information"
            }
        }

        binding.searchButton.setOnClickListener {
            val email = binding.searchEmail.text.toString()
            if (userExists(email)) {
                binding.tvSearchResult.text = getUserInfo(email)
            }
            else {
                binding.tvSearchResult.text = "User not found"
            }
        }
    }

    // function to count users, if users is not initialized yet, returns 0
    private fun countUsers(): Int {
        if (this::users.isInitialized) {
            return users.size
        }
        else {
            return 0
        }
    }

    // function to check if user exists with that email
    private fun userExists(email: String): Boolean {
        if (users.containsKey(email)) {
            return true
        }
        else {
            return false
        }
    }

    // function to get user info
    private fun getUserInfo(email: String): String {
        return "Full name: ${users[email]} \nEmail: $email"
    }

    /**
     * function from my previous project,
     * only accepting emails which end with acceptable @ and do not contain special characters
     * */
    private fun isValidEmail(email: String): Boolean {
        val specialChars = listOf('!', '#', '$', '%', '^', '&', '*', '(', ')', '+', '=', '{',
            '}', '[', ']', '|', '\\', ':', ';', '"', '\'' ,'<', '>', '?', '/')

        for (char in email) {
            if (char in specialChars) {
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
