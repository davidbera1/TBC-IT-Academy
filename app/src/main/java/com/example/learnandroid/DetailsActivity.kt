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
        // TEXT VIEWS SECTION
        binding.tvOverview.text = getString(R.string.overview)
        binding.tvOverview.textSize = 20f
        binding.tvOverview.setTextColor(getColor(R.color.black))


        binding.tvDetails.text = getString(R.string.details)
        binding.tvDetails.textSize = 18f
        binding.tvDetails.setTextColor(getColor(R.color.gray))


        binding.tvClock.text = getString(R.string.time)
        binding.tvClock.textSize = 18f


        binding.tvWeather.text = getString(R.string.temperature)
        binding.tvWeather.textSize = 18f


        binding.tvRating.text = getString(R.string.rating)
        binding.tvRating.textSize = 18f


        binding.tvMountainInfo.text = getString(R.string.mountain_info)
        binding.tvMountainInfo.textSize = 18f


        // IMAGE VIEWS SECTION
        binding.imgMountFuji.setImageResource(R.drawable.mount_fuji)

        binding.imgClock.setImageResource(R.drawable.clock)

        binding.imgWeather.setImageResource(R.drawable.weather)

        binding.imgStar.setImageResource(R.drawable.star)


        // BUTTONS SECTION
        binding.imgBtnBack.setBackgroundResource(R.drawable.circle_button)
        binding.imgBtnBack.setImageResource(R.drawable.back)


        binding.imgBtnBookmark.setBackgroundResource(R.drawable.circle_button)
        binding.imgBtnBookmark.setImageResource(R.drawable.bookmark)


    }

    private fun setUpListeners() {
        binding.imgBtnBack.setOnClickListener {
            finish()
        }
    }
}