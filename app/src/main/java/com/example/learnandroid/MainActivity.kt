package com.example.learnandroid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learnandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setUpListeners() {
        binding.tvSignUp.setOnClickListener {
            supportFragmentManager.popBackStack()
            makeRegisterFragmentVisible(true)
            supportFragmentManager.beginTransaction()
                .replace(R.id.RegisterFragment_container, RegisterFragment(), "register")
                .addToBackStack("register")
                .commit()
        }

        binding.btnSignIn.setOnClickListener {
            supportFragmentManager.popBackStack()
            makeLoginFragmentVisible(true)
            supportFragmentManager.beginTransaction()
                .replace(R.id.LoginFragment_container, LoginFragment(), "login")
                .addToBackStack("login")
                .commit()
        }
    }


    /** When fragment finishes, show main layout again */
    fun makeRegisterFragmentVisible(bool: Boolean) {
        if (bool) {
            binding.main.visibility = View.GONE
            binding.RegisterFragmentContainer.visibility = View.VISIBLE
            binding.LoginFragmentContainer.visibility = View.GONE
        }
        else{
            binding.main.visibility = View.VISIBLE
            binding.RegisterFragmentContainer.visibility = View.GONE
        }

    }
    fun makeLoginFragmentVisible(bool: Boolean) {
        if (bool) {
            binding.main.visibility = View.GONE
            binding.LoginFragmentContainer.visibility = View.VISIBLE
            binding.RegisterFragmentContainer.visibility = View.GONE
        }
        else {
            binding.main.visibility = View.VISIBLE
            binding.LoginFragmentContainer.visibility = View.GONE
        }
    }
}
