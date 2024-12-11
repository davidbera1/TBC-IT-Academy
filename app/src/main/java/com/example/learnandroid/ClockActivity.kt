package com.example.learnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learnandroid.databinding.ActivityClockBinding

class ClockActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClockBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        setUpListeners()

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun setUpViews() {
        binding.imgHome.setImageResource(R.drawable.home)
        binding.imgHome.isClickable = true


        binding.imgClock.setImageResource(R.drawable.clock)
        binding.imgClock.isClickable = true


        binding.imgHeart.setImageResource(R.drawable.heart)
        binding.imgHeart.isClickable = true


        binding.imgUser.setImageResource(R.drawable.user)
        binding.imgUser.isClickable = true


        binding.tvRecentlyViewed.text = getString(R.string.recently_viewed)
        binding.tvRecentlyViewed.textSize = 40f
    }

    private fun setUpListeners() {
        binding.imgHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.imgHeart.setOnClickListener {
            val intent = Intent(this, HeartActivity::class.java)
            startActivity(intent)
        }

        binding.imgUser.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }
    }
}