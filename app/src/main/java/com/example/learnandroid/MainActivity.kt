package com.example.learnandroid

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.learnandroid.databinding.ActivityMainBinding
import com.example.learnandroid.di.LanguagePreferenceEntryPoint
import com.example.learnandroid.utils.updateLocale
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val entryPoint = EntryPointAccessors.fromApplication(newBase, LanguagePreferenceEntryPoint::class.java)
        val languagePref = entryPoint.getLanguagePreference()

        val language = runBlocking { languagePref.getLanguage().first() }
        val updatedContext = updateLocale(newBase, language)

        super.attachBaseContext(updatedContext)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
