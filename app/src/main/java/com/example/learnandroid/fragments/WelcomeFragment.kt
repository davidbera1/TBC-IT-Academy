package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.databinding.FragmentWelcomeBinding
import com.example.learnandroid.datastore.UserSessionManager
import kotlinx.coroutines.launch

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
        val flow = UserSessionManager.getUserSession(requireContext())
        viewLifecycleOwner.lifecycleScope.launch {
            flow.collect { userSession ->
                if (userSession.isLoggedIn) {
                    val direction = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
                    findNavController().navigate(direction)
                }
            }
        }
    }
}