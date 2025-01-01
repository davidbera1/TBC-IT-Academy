package com.example.learnandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learnandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
    }

    private fun setUp() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ChatFragment())
            .commit()
    }
}

