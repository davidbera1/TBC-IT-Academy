package com.example.learnandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.learnandroid.databinding.FragmentAddNewAddressBinding

class AddNewAddressFragment : Fragment() {
    private var binding: FragmentAddNewAddressBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewAddressBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setUpListeners() {
        binding?.btnAddNewAddress?.setOnClickListener {
            if (validateFields()) {
                val addressName = binding?.etAddressName?.text.toString()
                val address = binding?.etAddress?.text.toString()
                val image = getIcon()

                (activity as MainActivity).addItems(image=image, addressName=addressName, address=address)
                (activity as MainActivity).makeFragmentVisible(false)
                Toast.makeText(requireContext(), getString(R.string.address_added_successfully), Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }

        // only one checkbox can be selected at a time
        binding?.cbHome?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding?.cbOffice?.isChecked = false
            }
        }

        binding?.cbOffice?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding?.cbHome?.isChecked = false
            }
        }
    }

    private fun validateFields(): Boolean{
        val addressName = binding?.etAddressName?.text.toString()
        val address = binding?.etAddress?.text.toString()

        if (addressName.isNotEmpty() && address.isNotEmpty()) {
            return true
        }
        else {
            Toast.makeText(requireContext(), getString(R.string.please_fill_all_the_fields_correctly), Toast.LENGTH_SHORT).show()
            return false
        }
    }

    /** Function for getting icon drawable id. */
    private fun getIcon(): Int {
        if (binding?.cbHome?.isChecked == true) {
            return R.drawable.home
        }
        else {
            return R.drawable.office
        }
    }
}