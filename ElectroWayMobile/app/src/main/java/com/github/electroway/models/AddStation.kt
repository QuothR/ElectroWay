package com.github.electroway.models;

import org.json.JSONObject

class AddStation(val address: String, val latitude: Double, val longitude: Double) {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("address", address)
            .put("latitude", latitude)
            .put("longitude", longitude)
    }
}