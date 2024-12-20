package com.example.learnandroid

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learnandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val users: MutableList<User> = mutableListOf()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpDefaultUsers()
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
            makeFragmentVisible(true)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddUserFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnSearch.setOnClickListener {
            val search = binding.etSearch.text.toString()
            if (searchUser(search)) {
                // not done yet
            }
            // user not found, show add user button
            else {
                binding.btnAddUser.visibility = View.VISIBLE
            }
        }
    }

    fun makeFragmentVisible(bool: Boolean) {
        if (bool) {
            binding.fragmentContainer.visibility = View.VISIBLE
            binding.main.visibility = View.GONE
        }
        else {
            binding.fragmentContainer.visibility = View.GONE
            binding.main.visibility = View.VISIBLE
        }
    }

    fun addUser(firstName: String, lastName: String, birthday: String, address: String, email: String, desc: String? = null) {
        users.add(User(
            id = users.size + 1,
            firstName = firstName,
            lastName = lastName,
            birthday = birthday,
            address = address,
            email = email,
            desc = desc
        ))
    }

    private fun searchUser(text: String): Boolean {
        val textToLower = text.lowercase()

        val result = users.any { user ->
            user.firstName.lowercase() == textToLower ||
            user.lastName.lowercase() == textToLower ||
            user.address.lowercase() == textToLower ||
            user.email.lowercase() == textToLower ||
            user.birthday == textToLower
        }

        return result
    }

    private fun setUpDefaultUsers() {
        users.add(User(
            id = 1,
            firstName = "გრიშა",
            lastName = "ონიანი",
            birthday = "1724647601641",
            address = "სტალინის სახლმუზეუმი",
            email = "grisha@mail.ru"
        ))
        users.add(User(
            id = 2,
            firstName = "Jemal",
            lastName = "Kakauridze",
            birthday = "1714647601641",
            address = "თბილისი, ლილოს მიტოვებული ქარხანა",
            email = "jemal@gmail.com"
        ))
        users.add(User(
            id = 2,
            firstName = "Omger",
            lastName = "Kakauridze",
            birthday = "1724647701641",
            address = "თბილისი, ასათიანი 18",
            email = "omger@gmail.com"
        ))
        users.add(User(
            id = 32,
            firstName = "ბორის",
            lastName = "გარუჩავა",
            birthday = "1714947701641",
            address = "თბილისი, იაშვილი 14",
            email = ""
        ))
        users.add(User(
            id =34,
            firstName = "აბთო",
            lastName = "სიხარულიძე",
            birthday = "1711947701641",
            address = "ფოთი",
            email = "tebzi@gmail.com",
            desc = null
        ))
    }

    private fun setResultText(text: String) {
        binding.tvResult.text = text
    }
}
