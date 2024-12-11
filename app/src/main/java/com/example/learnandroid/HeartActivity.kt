package com.example.learnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learnandroid.databinding.ActivityHeartBinding

class HeartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHeartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHeartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        setUpListeners()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun setUpViews() {
        binding.imgHome.setImageResource(R.drawable.home)
        binding.imgHome.isClickable = true


        binding.imgClock.setImageResource(R.drawable.clock)
        binding.imgClock.isClickable = true


        binding.imgHeart.setImageResource(R.drawable.heart)
        binding.imgHeart.isClickable = true


        binding.imgUser.setImageResource(R.drawable.user)
        binding.imgUser.isClickable = true

        binding.tvFavorites.text = getString(R.string.favorites)
        binding.tvFavorites.textSize = 40f
    }

    fun setUpListeners() {
        binding.imgClock.setOnClickListener {
            val intent = Intent(this, ClockActivity::class.java)
            startActivity(intent)
        }

        binding.imgHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.imgUser.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }
    }
}