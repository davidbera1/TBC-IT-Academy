package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnandroid.R
import com.example.learnandroid.adapter.FieldGroupAdapter
import com.example.learnandroid.databinding.FragmentRegisterBinding
import com.example.learnandroid.model.Field
import com.example.learnandroid.viewmodel.RegisterViewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private lateinit var fieldsData: List<List<Field>>
    private var fieldValues: Map<String, String> = mapOf()
    private val viewModel: RegisterViewModel by activityViewModels()
    private lateinit var adapter: FieldGroupAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        setUpListeners()
    }

    private fun setUpRecyclerView() {
        fieldsData = viewModel.fieldsData

        adapter = FieldGroupAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        adapter.submitList(fieldsData)
    }

    private fun setUpListeners() {
        binding.btnRegister.setOnClickListener {
            fieldValues = adapter.getFieldValues()

            register()
        }
    }

    private fun register(): Boolean {
        for (group in fieldsData) {
            for (field in group) {
                if (field.required && fieldValues[field.hint].isNullOrEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.field_is_required, field.hint), Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }

        viewModel.addUser(fieldValues)
        Toast.makeText(requireContext(), getString(R.string.registration_successful), Toast.LENGTH_SHORT).show()
        return true
    }
}