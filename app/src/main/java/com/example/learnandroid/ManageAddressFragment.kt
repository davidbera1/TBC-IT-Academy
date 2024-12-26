package com.example.learnandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.learnandroid.databinding.FragmentManageAddressBinding

class ManageAddressFragment : Fragment() {
    private var currentPosition: Int = 0
    private var operation: String = ""
    private var binding: FragmentManageAddressBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageAddressBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        currentPosition = (activity as MainActivity).currentPosition

        // check for which operation fragment was called, edit or create to show views accordingly
        operation = (activity as MainActivity).operation
        setUpViews()
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

                // call addItems() from MainActivity to add items and destroy Fragment
                (activity as MainActivity).addItems(image=image, addressName=addressName, address=address)
                Toast.makeText(requireContext(), getString(R.string.address_added_successfully), Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }

        // when choosing icon during creation only one checkbox can be selected at a time
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

        // when updating icon only one new checkbox can be selected at a time
        binding?.cbNewHome?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding?.cbNewOffice?.isChecked = false
            }
        }

        binding?.cbNewOffice?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding?.cbNewHome?.isChecked = false
            }
        }

        binding?.btnEditAddress?.setOnClickListener {
            val addressName = binding?.etNewAddressName?.text.toString()
            val address = binding?.etNewAddress?.text.toString()
            val image = getIconEdit()

            // call updateItem() from MainActivity to update items and destroy Fragment
            (activity as MainActivity).updateItem(addressName=addressName, address=address, image=image, position=currentPosition)
            Toast.makeText(requireContext(), getString(R.string.address_updated_successfully), Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    /** Function for checking operation type to call appropriate function */
    private fun setUpViews() {
        if (operation == "edit") {
            showEdit(true)
            showCreate(false)
        }
        else if (operation == "create") {
            showEdit(false)
            showCreate(true)
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

    /** Function for getting icon drawable id */
    private fun getIcon(): Int {
        if (binding?.cbHome?.isChecked == true) {
            return R.drawable.home
        }
        else {
            return R.drawable.office
        }
    }

    /** Function for getting new icon drawable id. Returns 0 if neither is checked */
    private fun getIconEdit(): Int {
        if (binding?.cbNewHome?.isChecked == true) {
            return R.drawable.home
        }
        else if(binding?.cbNewOffice?.isChecked == true) {
            return R.drawable.office
        }
        else {
            return 0
        }
    }

    /** Function for showing only views that are needed for creating address */
    private fun showCreate(boolean: Boolean) {
        if (boolean) {
            binding?.tvAddNewAddress?.visibility = View.VISIBLE
            binding?.etAddressName?.visibility = View.VISIBLE
            binding?.etAddress?.visibility = View.VISIBLE
            binding?.tvChooseIcon?.visibility = View.VISIBLE
            binding?.cbHome?.visibility = View.VISIBLE
            binding?.cbOffice?.visibility = View.VISIBLE
            binding?.cbOffice?.visibility = View.VISIBLE
            binding?.imgHome?.visibility = View.VISIBLE
            binding?.imgOffice?.visibility = View.VISIBLE
            binding?.btnAddNewAddress?.visibility = View.VISIBLE
        }
        else {
            binding?.tvAddNewAddress?.visibility = View.GONE
            binding?.etAddressName?.visibility = View.GONE
            binding?.etAddress?.visibility = View.GONE
            binding?.tvChooseIcon?.visibility = View.GONE
            binding?.cbHome?.visibility = View.GONE
            binding?.cbOffice?.visibility = View.GONE
            binding?.cbOffice?.visibility = View.GONE
            binding?.imgHome?.visibility = View.GONE
            binding?.imgOffice?.visibility = View.GONE
            binding?.btnAddNewAddress?.visibility = View.GONE
        }

    }

    /** Function for showing only views that are needed for updating address */
    private fun showEdit(boolean: Boolean) {
        if (boolean) {
            binding?.tvEditAddress?.visibility = View.VISIBLE
            binding?.etNewAddressName?.visibility = View.VISIBLE
            binding?.etNewAddress?.visibility = View.VISIBLE
            binding?.tvUpdateIcon?.visibility = View.VISIBLE
            binding?.cbNewHome?.visibility = View.VISIBLE
            binding?.cbNewOffice?.visibility = View.VISIBLE
            binding?.imgNewHome?.visibility = View.VISIBLE
            binding?.imgNewOffice?.visibility = View.VISIBLE
            binding?.btnEditAddress?.visibility = View.VISIBLE
        }
        else {
            binding?.tvEditAddress?.visibility = View.GONE
            binding?.etNewAddressName?.visibility = View.GONE
            binding?.etNewAddress?.visibility = View.GONE
            binding?.tvUpdateIcon?.visibility = View.GONE
            binding?.cbNewHome?.visibility = View.GONE
            binding?.cbNewOffice?.visibility = View.GONE
            binding?.imgNewHome?.visibility = View.GONE
            binding?.imgNewOffice?.visibility = View.GONE
            binding?.btnEditAddress?.visibility = View.GONE
        }
    }
}