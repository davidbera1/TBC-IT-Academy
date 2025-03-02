package com.example.learnandroid.presentation.ui.welcome

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentWelcomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.model.Recipe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(FragmentWelcomeBinding::inflate) {

    private val viewModel: WelcomeViewModel by viewModels()

    override fun start() {
        checkUserSession()
        observeLanguageAndUpdateUI()
    }

    override fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
            findNavController().navigate(direction)
        }

        binding.btnRegister.setOnClickListener {
            val direction = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment()
            findNavController().navigate(direction)
        }

        binding.imgLanguage.setOnClickListener {
            changeLanguage()
        }
    }

    private fun changeLanguage() {
        if (viewModel.language.value == "en") {
            viewModel.saveLanguage("ka")
        } else if (viewModel.language.value == "ka") {
            viewModel.saveLanguage("en")
        }
        // adding small delay before recreating activity because otherwise sometimes it has no "effect"
        viewLifecycleOwner.lifecycleScope.launch {
            delay(100L)
            requireActivity().recreate()
        }
    }

    private fun observeLanguageAndUpdateUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.language.collect { language ->
                if (language == "ka") {
                    binding.imgLanguage.setImageResource(R.drawable.georgia)
                } else if (language == "en") {
                    binding.imgLanguage.setImageResource(R.drawable.usa)
                }
            }
        }
    }

    private fun checkUserSession() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    val direction =
                        WelcomeFragmentDirections.actionWelcomeFragmentToBottomNavBarContainerFragment()
                    findNavController().navigate(direction)
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}