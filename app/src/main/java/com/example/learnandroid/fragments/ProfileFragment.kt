package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentProfileBinding
import com.example.learnandroid.datastore.UserSessionManager
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateEmailText()
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            clearUserSession()

            val direction = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(direction)

            Toast.makeText(requireContext(), getString(R.string.logged_out), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateEmailText() {
        val flow = UserSessionManager.getUserSession(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            flow.collect { userSession ->
                binding.tvEmail.text = getString(R.string.welcome_email, userSession.email)
            }
        }
    }

    private fun clearUserSession() {
        viewLifecycleOwner.lifecycleScope.launch {
            UserSessionManager.clearUserSession(requireContext())
        }
    }
}