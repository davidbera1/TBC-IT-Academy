package com.example.learnandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learnandroid.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private var binding: FragmentRegisterBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setUpListeners()

        return binding!!.root
    }

    private fun setUpListeners() {
        binding?.imgBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding?.tvSignIn?.setOnClickListener {
            parentFragmentManager.popBackStack()
            (activity as MainActivity).makeLoginFragmentVisible(true)
            parentFragmentManager.beginTransaction()
                .replace(R.id.LoginFragment_container, LoginFragment(), "login")
                .addToBackStack("login")
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        (activity as MainActivity).makeRegisterFragmentVisible(false)
    }
}