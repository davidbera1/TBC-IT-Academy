package com.example.learnandroid.presentation.ui.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.databinding.FragmentProfileBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.ui.bottom_nav_bar_container.BottomNavBarContainerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun start() {
        observeEmailAndUpdateUI()
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val direction = BottomNavBarContainerFragmentDirections.actionBottomNavBarContainerFragmentToWelcomeFragment()
            findNavController().navigate(direction)
        }
    }

    private fun observeEmailAndUpdateUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.email.collect { email ->
                binding.tvEmail.text = email
            }
        }
    }
}