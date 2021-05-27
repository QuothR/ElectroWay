package com.github.electroway.models

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng

class GeocoderAddress(context: Context) {
    private val geocoder = Geocoder(context)

    fun getFromLatLng(latLng: LatLng): String {
        val addresses =
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        return if (addresses.size > 0 && addresses[0].maxAddressLineIndex != -1)
            addresses[0].getAddressLine(0)
        else
            "${latLng.latitude}, ${latLng.longitude}"
    }
}