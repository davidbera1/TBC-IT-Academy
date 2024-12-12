package com.example.learnandroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learnandroid.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
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
        binding.tvOverview.text = getString(R.string.overview)

        binding.tvDetails.text = getString(R.string.details)

        binding.tvClock.text = getString(R.string.time)

        binding.tvWeather.text = getString(R.string.temperature)

        binding.tvRating.text = getString(R.string.rating)

        binding.tvMountainInfo.text = getString(R.string.mountain_info)
    }

    private fun setUpListeners() {
        binding.imgBtnBack.setOnClickListener {
            finish()
        }
    }
}