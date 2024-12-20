package com.example.learnandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.learnandroid.databinding.FragmentAddUserBinding

class AddUserFragment : Fragment() {
    private var binding: FragmentAddUserBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)

        setUpListeners()

        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setUpListeners() {

        binding?.btnSaveUser?.setOnClickListener {
            val firstName = binding?.etFirstName?.text.toString()
            val lastName = binding?.etLastName?.text.toString()
            val birthday = binding?.etBirthday?.text.toString()
            val address = binding?.etAddress?.text.toString()
            val email = binding?.etEmail?.text.toString()
            val desc = binding?.etDesc?.text.toString()

            if (validateFields()) {
                (activity as MainActivity).addUser(firstName, lastName, birthday, address, email, desc)
                parentFragmentManager.popBackStack()
                (activity as MainActivity).makeFragmentVisible(false)
            }
            else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateFields(): Boolean {
        val firstName = binding?.etFirstName?.text.toString()
        val lastName = binding?.etLastName?.text.toString()
        val birthday = binding?.etBirthday?.text.toString()
        val address = binding?.etAddress?.text.toString()
        val email = binding?.etEmail?.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && birthday.isNotEmpty() &&
            address.isNotEmpty() && email.isNotEmpty()) {
            return true
        }
        else {
            return false
        }
    }


}