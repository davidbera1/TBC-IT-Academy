package com.example.learnandroid.presentation.ui.profile

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentProfileBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.util.launchViewLifecycleOwnerScopeWithStartedState
import com.example.learnandroid.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun start() {
        updateEmailText()
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.clearUserSession()

            val direction = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(direction)

            requireContext().showToast(getString(R.string.logged_out))
        }
    }

    private fun updateEmailText() {
        val flow = viewModel.getUserSession()
        launchViewLifecycleOwnerScopeWithStartedState {
            flow.collect { userSession ->
                binding.tvEmail.text = getString(R.string.welcome_email, userSession?.email)
            }
        }
    }
}