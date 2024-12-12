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

        // TEXT VIEWS SECTION
        binding.tvGreeting.text = getString(R.string.greeting)

        binding.tvExplore.text = getString(R.string.explore)

        binding.tvPopularPlaces.text = getString(R.string.popular_places)

        binding.tvMountFujiDescription.text = getString(R.string.mount_fuji_tokyo)

        binding.tvMountFujiLocation.text = getString(R.string.tokyo_japan)

        binding.tvRating.text = getString(R.string.rating)

        // BUTTONS SECTION
        binding.btnViewAll.text = getString(R.string.view_all)

        binding.btnMostViewed.text = getString(R.string.most_viewed)

        binding.btnNearby.text = getString(R.string.nearby)

        binding.btnLatest.text = getString(R.string.latest)

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

        binding.btnViewAll.setOnClickListener {
            // just to be clickable
        }

        binding.imgBtnFavorite.setOnClickListener {
            // just to be clickable
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