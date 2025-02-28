package com.example.learnandroid.presentation.place_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.learnandroid.databinding.FragmentPlaceDetailBottomSheetBinding
import com.example.learnandroid.presentation.model.ParcelablePlace
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PlaceDetailBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPlaceDetailBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val args: PlaceDetailBottomSheetFragmentArgs by navArgs()
    private lateinit var place: ParcelablePlace

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        place = args.place
        _binding = FragmentPlaceDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = place.placeTitle
        binding.tvAddress.text = place.address
        binding.tvLatitude.text = place.latitude.toString()
        binding.tvLongitude.text = place.longitude.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
