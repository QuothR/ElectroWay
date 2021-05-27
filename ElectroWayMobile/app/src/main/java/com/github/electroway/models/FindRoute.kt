package com.github.electroway.models

import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray
import org.json.JSONObject

class FindRoute(
    val locationsCoords: Array<LatLng>,
    val carId: Int,
    val currentChargeInkW: Double,
    val avoid: String = "unpavedRoads"
) {
    fun getJson(): JSONObject {
        val coords = JSONArray()
        for (coord in locationsCoords) {
            coords.put(JSONObject().put("lat", coord.latitude).put("lon", coord.longitude))
        }
        return JSONObject()
            .put("locationsCoords", coords)
            .put("avoid", avoid)
            .put(
                "carData",
                JSONObject()
                    .put("carId", carId)
                    .put("currentChargeInkW", currentChargeInkW)
            )
    }
}