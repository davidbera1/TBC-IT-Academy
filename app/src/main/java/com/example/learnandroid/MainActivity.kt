package com.example.learnandroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learnandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var activeUsers: Int = 0
    private var removedUsers: Int = 0
    private lateinit var users: MutableMap<String, User>
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        users = mutableMapOf<String, User>()

        setUpListeners()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpListeners() {
        binding.btnAddUser.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (validateFields() && isValidEmail(email)) {
                val user = User(
                    firstName = binding.etFirstName.text.toString(),
                    lastName = binding.etLastName.text.toString(),
                    age = binding.etAge.text.toString().toInt(),
                    email = email
                )
                addUser(user)
            }
            else {
                Toast.makeText(this, getString(R.string.please_fill_all_fields_correctly), Toast.LENGTH_SHORT).show()
                binding.tvStatus.setTextColor(getColor(R.color.red))
                binding.tvStatus.text = getString(R.string.error)
            }
        }

        binding.btnRemoveUser.setOnClickListener {
            val email = binding.etEmail.text.toString()
            removeUser(email)
        }

        binding.btnUpdateUser.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val age = binding.etAge.text.toString().toIntOrNull()

            if(age != null && age < 0) {
                Toast.makeText(this, getString(R.string.age_must_be_positive_number), Toast.LENGTH_SHORT).show()
                binding.tvStatus.setTextColor(getColor(R.color.red))
                binding.tvStatus.text = getString(R.string.error)
            }
            else if (age != null) {
                updateUser(
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    age = age
                )
            }
            else {
                updateUser(
                    email = email,
                    firstName = firstName,
                    lastName = lastName
                )
            }
        }

        binding.btnSearch.setOnClickListener {
            val email = binding.etSearch.text.toString()
            searchUserInfo(email)
        }
    }

    /** Function for adding users. Using Email as unique ID. If user gets added, activeUsers
     *  increases and sets result text color to green and text - success, otherwise - red, error. */
    private fun addUser(user: User) {
        if (user.email !in users) {
            users.put(user.email, user)
            activeUsers++

            Toast.makeText(this, getString(R.string.user_added_successfully), Toast.LENGTH_SHORT).show()
            binding.tvStatus.setTextColor(getColor(R.color.green))
            binding.tvStatus.text = getString(R.string.success)
            // update activeUsers count
            binding.tvActiveUsers.text = "Active Users: $activeUsers"
        }
        else {
            Toast.makeText(this, getString(R.string.user_with_that_email_already_exists), Toast.LENGTH_SHORT).show()
            binding.tvStatus.setTextColor(getColor(R.color.red))
            binding.tvStatus.text = getString(R.string.error)
        }
    }

    /** Function for removing users with email. Does the same with texts as addUser() */
    private fun removeUser(email: String) {
        if (email in users) {
            users.remove(email)
            removedUsers++
            activeUsers--
            Toast.makeText(this, getString(R.string.user_deleted_successfully), Toast.LENGTH_SHORT).show()
            binding.tvStatus.setTextColor(getColor(R.color.green))
            binding.tvStatus.text = getString(R.string.success)
            // update counts
            binding.tvActiveUsers.text = "Active Users: $activeUsers"
            binding.tvRemovedUsers.text = "Removed Users: $removedUsers"
        }
        else {
            Toast.makeText(this, getString(R.string.user_does_not_exist), Toast.LENGTH_SHORT).show()
            binding.tvStatus.setTextColor(getColor(R.color.red))
            binding.tvStatus.text = getString(R.string.error)
        }
    }

    /** Function for updating users. Can be used if some values are not passed at all. Using Email as
     * unique ID. Does the ame with texts as previous functions */
    private fun updateUser(firstName: String="", lastName: String="", age: Int=-1, email: String) {
        if (email in users) {

            if(firstName != "") {
                users[email]?.firstName = firstName
            }

            if(lastName != "") {
                users[email]?.lastName = lastName
            }

            if(age != -1 && age >= 0) {
                users[email]?.age = age
            }

            Toast.makeText(this, getString(R.string.user_info_updated_successfully), Toast.LENGTH_SHORT).show()
            binding.tvStatus.setTextColor(getColor(R.color.green))
            binding.tvStatus.text = getString(R.string.success)
        }
        else {
            Toast.makeText(this, getString(R.string.user_does_not_exist), Toast.LENGTH_SHORT).show()
            binding.tvStatus.setTextColor(getColor(R.color.red))
            binding.tvStatus.text = getString(R.string.error)
        }
    }

    /** Function for validating fields and checking if age is positive number */
    private fun validateFields(): Boolean {
        if (binding.etFirstName.text.toString().isNotEmpty() &&
            binding.etLastName.text.toString().isNotEmpty() &&
            binding.etAge.text.toString().toIntOrNull() != null &&
            binding.etAge.text.toString().toInt() >= 0 &&
            binding.etEmail.text.toString().isNotEmpty()
            ) {
            return true
        }
        else {
            return false
        }
    }

    /** Check if email has valid @ ending, has at least 6 characters before @ and do not contain
     * special characters. */
    private fun isValidEmail(email: String): Boolean {
        val specialChars = listOf('!', '#', '$', '%', '^', '&', '*', '(', ')', '+', '=', '{',
            '}', '[', ']', '|', '\\', ':', ';', '"', '\'' ,'<', '>', '?', '/')

        for (char in email) {
            if (char in specialChars) {
                return false
            }
        }

        var atCount = 0 // at means - @ symbol, check if there is more than 1 @
        for (char in email) {
            if (char == '@') {
                atCount++
            }
        }
        if (atCount > 1) {
            return false
        }

        if ((
            email.endsWith("@gmail.com") ||
            email.endsWith("@mail.ru") ||
            email.endsWith("@yahoo.com") ||
            email.endsWith("@outlook.com") ||
            email.endsWith("@hotmail.com") ||
            email.endsWith("@icloud.com")) &&
            email.substringBefore("@").length >= 6 // before @ should be at least 6 characters
        ) {
            return true
        }
        else {
            return false
        }
    }

    /** Function for searching user info by email. */
    private fun searchUserInfo(email: String) {
        if (isValidEmail(email) && email in users) {
            binding.tvSearchResult.text = "Full Name: ${users[email]?.firstName} ${users[email]?.lastName}\nAge: ${users[email]?.age}\nEmail: $email"
            binding.tvStatus.setTextColor(getColor(R.color.green))
            binding.tvStatus.text = getString(R.string.success)
        }
        else if (!isValidEmail(email)){
            binding.tvSearchResult.text = ""
            Toast.makeText(this, getString(R.string.please_enter_valid_email), Toast.LENGTH_SHORT).show()
            binding.tvStatus.setTextColor(getColor(R.color.red))
            binding.tvStatus.text = getString(R.string.error)
        }
        else {
            binding.tvSearchResult.text = ""
            Toast.makeText(this, getString(R.string.user_does_not_exist), Toast.LENGTH_SHORT).show()
            binding.tvStatus.setTextColor(getColor(R.color.red))
            binding.tvStatus.text = getString(R.string.error)
        }
    }
}
