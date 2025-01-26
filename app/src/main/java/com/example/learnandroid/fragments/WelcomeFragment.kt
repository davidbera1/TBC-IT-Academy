package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkSavedUserSession()
    }

    override fun setUpListeners() {
        binding.btnRegister.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment()
            findNavController().navigate(direction)
        }

        binding.btnLogin.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
            findNavController().navigate(direction)
        }
    }

    private fun checkSavedUserSession() {
        val isLoggedIn = getUserSessionSharedPreferences().getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            val direction = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.welcomeFragment, true)
                .build()

            findNavController().navigate(direction, navOptions = navOptions)
        }
    }
}