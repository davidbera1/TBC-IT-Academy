package com.example.learnandroid.presentation.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Place(
    val latitude: Double,
    val longitude: Double,
    val placeTitle: String,
    val address: String
) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(latitude, longitude)
    override fun getTitle(): String = placeTitle
    override fun getSnippet(): String = address
}