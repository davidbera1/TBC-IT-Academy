package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
    }

    private fun setUp() {
        startSelectLevelFragment()
    }

    private fun startSelectLevelFragment() {
        // handle lambda callback
        val selectLevelFragment = SelectLevelFragment().apply {
            onLevelSelected = { levelType ->
                startGameFragment(levelType=levelType)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, selectLevelFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun startGameFragment(levelType: LevelType) {
        val gameFragment = GameFragment.newInstance(levelType).apply {
            onBackPressed = { startSelectLevelFragment() }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, gameFragment)
            .commit()
    }
}
