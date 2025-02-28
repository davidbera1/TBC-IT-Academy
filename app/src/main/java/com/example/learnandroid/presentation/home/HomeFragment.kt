package com.example.learnandroid.presentation.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.learnandroid.R
import com.example.learnandroid.databinding.FragmentHomeBinding
import com.example.learnandroid.presentation.base.BaseFragment
import com.example.learnandroid.presentation.model.ParcelablePlace
import com.example.learnandroid.presentation.model.Place
import com.example.learnandroid.presentation.place_renderer.PlaceRenderer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mapFragment: SupportMapFragment? = null
    private val places = mutableListOf<Place>()
    private lateinit var googleMap: GoogleMap

    override fun start() {
        requestPermissions()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        loadMap()
        observePlacesAndAddMarkers()
    }

    override fun setUpListeners() {
        setupZoomButtons()
    }

    private fun setupZoomButtons() {
        binding.btnZoomIn.setOnClickListener {
            googleMap.animateCamera(CameraUpdateFactory.zoomIn())
        }
        binding.btnZoomOut.setOnClickListener {
            googleMap.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }

    private fun loadMap() {
        mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync { map ->
            googleMap = map
            setupZoomButtons()
            getUserCurrentLocation(map)
        }
    }

    private fun requestPermissions() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {}
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {}
                else -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", requireContext().packageName, null)
                    }
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.please_enable_location_in_settings),
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(intent)
                }
            }
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun getUserCurrentLocation(googleMap: GoogleMap) {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)
                googleMap.addMarker(
                    MarkerOptions().position(currentLatLng).title("Current Location")
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }
    }

    private fun observePlacesAndAddMarkers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.places.collect {
                binding.progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE
                it.error?.let { error ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
                places.clear()
                places.addAll(it.places)
                if (::googleMap.isInitialized) {
                    googleMap.clear()
                    addClusteredMarkers(googleMap)
                }
            }
        }
    }

    private fun addClusteredMarkers(googleMap: GoogleMap) {
        if (places.isEmpty()) return

        val clusterManager = ClusterManager<Place>(requireContext(), googleMap)
        clusterManager.renderer = PlaceRenderer(requireContext(), googleMap, clusterManager)
        clusterManager.addItems(places)
        clusterManager.cluster()

        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }

        clusterManager.setOnClusterItemClickListener { place ->
            val action = HomeFragmentDirections.actionHomeFragmentToPlaceDetailBottomSheetFragment(
                ParcelablePlace(
                    placeTitle = place.placeTitle,
                    address = place.address,
                    latitude = place.latitude,
                    longitude = place.longitude
                )
            )
            findNavController().navigate(action)
            true
        }

        googleMap.setOnMarkerClickListener(clusterManager)
    }
}
