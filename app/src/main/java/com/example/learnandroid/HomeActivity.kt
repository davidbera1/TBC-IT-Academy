package com.example.learnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learnandroid.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
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

        binding.etSearch.hint = getString(R.string.search)
        binding.etSearch.setBackgroundResource(R.drawable.edittext_corners_rounded)


        // TEXT VIEWS SECTION
        binding.tvGreeting.text = getString(R.string.greeting)
        binding.tvGreeting.setTextColor(getColor(R.color.black))
        binding.tvGreeting.textSize = 30f


        binding.tvExplore.text = getString(R.string.explore)
        binding.tvExplore.textSize = 16f
        binding.tvExplore.setTextColor(getColor(R.color.gray))


        binding.tvPopularPlaces.text = getString(R.string.popular_places)
        binding.tvPopularPlaces.textSize = 16f
        binding.tvPopularPlaces.setTextColor(getColor(R.color.black))


        binding.tvMountFujiDescription.text = getString(R.string.mount_fuji_tokyo)
        binding.tvMountFujiDescription.setTextColor(getColor(R.color.white))
        binding.tvMountFujiDescription.textSize = 18f


        // BUTTONS SECTION
        binding.btnViewAll.text = getString(R.string.view_all)
        binding.btnViewAll.setBackgroundResource(R.drawable.invisible_background)
        binding.btnViewAll.setTextColor(getColor(R.color.gray))
        binding.btnViewAll.isAllCaps = false


        binding.btnMostViewed.text = getString(R.string.most_viewed)
        binding.btnMostViewed.setBackgroundResource(R.drawable.lightblack_button_corners_rounded)
        binding.btnMostViewed.setTextColor(getColor(R.color.white))
        binding.btnMostViewed.isAllCaps = false


        binding.btnNearby.text = getString(R.string.nearby)
        binding.btnNearby.setBackgroundResource(R.drawable.lightgray_button_corners_rounded)
        binding.btnNearby.setTextColor(getColor(R.color.gray))
        binding.btnNearby.isAllCaps = false


        binding.btnLatest.text = getString(R.string.latest)
        binding.btnLatest.setBackgroundResource(R.drawable.lightgray_button_corners_rounded)
        binding.btnLatest.setTextColor(getColor(R.color.gray))
        binding.btnLatest.isAllCaps = false


        // IMAGES SECTION
        binding.imgMountFuji.setImageResource(R.drawable.mount_fuji)
        binding.imgMountFuji.isClickable = true


        binding.imgUserPhoto.setImageResource(R.drawable.user_photo)
        binding.imgUserPhoto.isClickable = true


        binding.imgHome.setImageResource(R.drawable.home)
        binding.imgHome.isClickable = true


        binding.imgClock.setImageResource(R.drawable.clock)
        binding.imgClock.isClickable = true


        binding.imgHeart.setImageResource(R.drawable.heart)
        binding.imgHeart.isClickable = true


        binding.imgUser.setImageResource(R.drawable.user)
        binding.imgUser.isClickable = true

    }

    private fun setUpListeners() {
        // starts details activity after clicking the image
        binding.imgMountFuji.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }

        binding.imgUserPhoto.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        binding.btnMostViewed.setOnClickListener {
            highlightMostViewed()
        }

        binding.btnNearby.setOnClickListener {
            highlightNearby()
        }

        binding.btnLatest.setOnClickListener{
            highlightLatest()
        }

        binding.imgClock.setOnClickListener {
            val intent = Intent(this, ClockActivity::class.java)
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

    private fun highlightMostViewed() {
        binding.btnMostViewed.setBackgroundResource(R.drawable.lightblack_button_corners_rounded)
        binding.btnMostViewed.setTextColor(getColor(R.color.white))
        // gray out other buttons
        binding.btnNearby.setBackgroundResource(R.drawable.lightgray_button_corners_rounded)
        binding.btnNearby.setTextColor(getColor(R.color.gray))
        binding.btnLatest.setBackgroundResource(R.drawable.lightgray_button_corners_rounded)
        binding.btnLatest.setTextColor(getColor(R.color.gray))
    }

    private fun highlightNearby() {
        binding.btnNearby.setBackgroundResource(R.drawable.lightblack_button_corners_rounded)
        binding.btnNearby.setTextColor(getColor(R.color.white))
        // gray out other buttons
        binding.btnMostViewed.setBackgroundResource(R.drawable.lightgray_button_corners_rounded)
        binding.btnMostViewed.setTextColor(getColor(R.color.gray))
        binding.btnLatest.setBackgroundResource(R.drawable.lightgray_button_corners_rounded)
        binding.btnLatest.setTextColor(getColor(R.color.gray))
    }

    private fun highlightLatest() {
        binding.btnLatest.setBackgroundResource(R.drawable.lightblack_button_corners_rounded)
        binding.btnLatest.setTextColor(getColor(R.color.white))
        // gray out other buttons
        binding.btnMostViewed.setBackgroundResource(R.drawable.lightgray_button_corners_rounded)
        binding.btnMostViewed.setTextColor(getColor(R.color.gray))
        binding.btnNearby.setBackgroundResource(R.drawable.lightgray_button_corners_rounded)
        binding.btnNearby.setTextColor(getColor(R.color.gray))
    }
}