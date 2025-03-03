package com.example.learnandroid.presentation.ui.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentProfileBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.ui.bottom_nav_bar_container.BottomNavBarContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun start() {
        observeEmailAndUpdateUI()
        observeLanguageAndUpdateUI()
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val direction = BottomNavBarContainerFragmentDirections.actionBottomNavBarContainerFragmentToWelcomeFragment()
            findNavController().navigate(direction)
        }

        binding.imgLanguage.setOnClickListener {
            changeLanguage()
        }

        binding.tvChangeLanguage.setOnClickListener {
            changeLanguage()
        }
    }

    private fun observeEmailAndUpdateUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.email.collect { email ->
                binding.tvEmail.text = email
            }
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
}