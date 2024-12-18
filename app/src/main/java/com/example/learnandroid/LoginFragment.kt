package com.example.learnandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learnandroid.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setUpListeners()

        return binding!!.root
    }

    private fun setUpListeners() {
        binding?.imgBack?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding?.tvSignUp?.setOnClickListener {
            parentFragmentManager.popBackStack()
            (activity as MainActivity).makeRegisterFragmentVisible(true)
            parentFragmentManager.beginTransaction()
                .replace(R.id.RegisterFragment_container, RegisterFragment(), "register")
                .addToBackStack("register")
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        (activity as MainActivity).makeLoginFragmentVisible(false)
    }
}