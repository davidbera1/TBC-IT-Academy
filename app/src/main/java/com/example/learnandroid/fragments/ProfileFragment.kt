package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = getUserSessionSharedPreferences().getString("email", "")

        binding.tvEmail.text = getString(R.string.email_useremail, email)
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            clearUserSession()

            val direction = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(findNavController().graph.id, true)
                .build()

            findNavController().navigate(direction, navOptions=navOptions)

            Toast.makeText(requireContext(), getString(R.string.logged_out), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun clearUserSession() {
        val editor = getUserSessionSharedPreferences().edit()

        editor?.apply {
            clear()
            apply()
        }
    }
}