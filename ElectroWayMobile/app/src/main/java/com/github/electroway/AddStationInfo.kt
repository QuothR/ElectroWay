package com.github.electroway;

import org.json.JSONObject

class AddStationInfo(val address: String, val latitude: Double, val longitude: Double) {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("address", address)
            .put("latitude", latitude)
            .put("longitude", longitude)
    }
}