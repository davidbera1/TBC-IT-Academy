package com.example.learnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learnandroid.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
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

    fun setUpViews() {

        binding.tvUsername.text = getString(R.string.username)
        binding.tvUsername.textSize = 40f


        // IMAGES SECTION
        binding.imgHome.setImageResource(R.drawable.home)
        binding.imgHome.isClickable = true


        binding.imgClock.setImageResource(R.drawable.clock)
        binding.imgClock.isClickable = true


        binding.imgHeart.setImageResource(R.drawable.heart)
        binding.imgHeart.isClickable = true


        binding.imgUser.setImageResource(R.drawable.user)
        binding.imgUser.isClickable = true


        binding.imgUserPhoto.setImageResource(R.drawable.user_photo)


        // BUTTONS SECTON
        binding.btnEditPersonalInfo.text = getString(R.string.edit_personal_info)
        binding.btnEditPersonalInfo.setBackgroundResource(R.drawable.lightgray_button_corners_rounded)

        binding.btnLogout.text = getString(R.string.logout)
        binding.btnLogout.setBackgroundResource(R.drawable.lightgray_button_corners_rounded)
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

        binding.imgHeart.setOnClickListener {
            val intent = Intent(this, HeartActivity::class.java)
            startActivity(intent)
        }
    }
}