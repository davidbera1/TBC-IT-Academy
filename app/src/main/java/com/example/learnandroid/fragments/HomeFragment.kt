package com.example.learnandroid.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = activity?.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "")

        binding.tvEmail.text = getString(R.string.email_useremail, email)
    }

    override fun setUpListeners() {
        binding.btnLogout.setOnClickListener {
            clearUserSession()
            // findNavController().navigateUp() doesn't work when app gets restarted and then user
            // logs out, it navigates to welcome fragment instead of login fragment
            val direction = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(findNavController().graph.id, true)
                .build()

            findNavController().navigate(direction, navOptions=navOptions)

            Toast.makeText(requireContext(), getString(R.string.logged_out), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun clearUserSession() {
        val sharedPreferences = activity?.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        editor?.apply {
            clear()
            apply()
        }
    }
}