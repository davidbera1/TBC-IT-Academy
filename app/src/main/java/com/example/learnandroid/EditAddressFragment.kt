package com.example.learnandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.learnandroid.databinding.FragmentAddNewAddressBinding
import com.example.learnandroid.databinding.FragmentEditAddressBinding

class EditAddressFragment : Fragment() {
    private var currentPosition: Int = 0
    private var binding: FragmentEditAddressBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAddressBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentPosition = (activity as MainActivity).currentPosition
        setUpListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setUpListeners() {
        binding?.btnEditAddress?.setOnClickListener {
            val addressName = binding?.etAddressName?.text.toString()
            val address = binding?.etAddress?.text.toString()
            val image = getIcon()

            // call updateItem() from MainActivity to update items and destroy Fragment
            (activity as MainActivity).updateItem(addressName=addressName, address=address, image=image, position=currentPosition)
            (activity as MainActivity).makeFragmentVisible(false)
            Toast.makeText(requireContext(), getString(R.string.address_updated_successfully), Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
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

    /** Function for getting icon drawable id. Returns 0 if neither checkbox is checked */
    private fun getIcon(): Int {
        if (binding?.cbHome?.isChecked == true) {
            return R.drawable.home
        }
        else if (binding?.cbOffice?.isChecked == true) {
            return R.drawable.office
        }
        else {
            return 0
        }
    }

}