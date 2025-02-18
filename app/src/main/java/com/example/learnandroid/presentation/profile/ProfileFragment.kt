package com.example.learnandroid.presentation.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentProfileBinding
import com.example.learnandroid.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateEmailText()
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            viewModel.clearUserSession()

            val direction = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(direction)

            Toast.makeText(requireContext(), getString(R.string.logged_out), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateEmailText() {
        val flow = viewModel.getUserSession()
        viewLifecycleOwner.lifecycleScope.launch {
            flow.collect { userSession ->
                binding.tvEmail.text = getString(R.string.welcome_email, userSession.email)
            }
        }
    }
}