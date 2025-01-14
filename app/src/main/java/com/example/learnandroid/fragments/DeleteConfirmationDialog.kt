package com.example.learnandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.learnandroid.databinding.FragmentDeleteConfirmationDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeleteConfirmationDialog(var onYesButtonClicked: (() -> Unit)? = null) : BottomSheetDialogFragment() {

    private var _binding: FragmentDeleteConfirmationDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeleteConfirmationDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnYes.setOnClickListener {
            onYesButtonClicked?.invoke()
            dismiss()
        }

        binding.btnNo.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}