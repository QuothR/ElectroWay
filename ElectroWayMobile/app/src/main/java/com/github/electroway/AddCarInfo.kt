package com.github.electroway

import org.json.JSONObject
import java.io.Serializable

class AddCarInfo(
    val id: Int?,
    val model: String,
    val year: Int,
    val batteryCapacity: Double,
    val chargingCapacity: Double,
    val plugType: String,
    val vehicleMaxSpeed: Double,
    val auxiliaryKwh: Double
) : Serializable {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("model", model)
            .put("year", year)
            .put("batteryCapacity", batteryCapacity)
            .put("chargingCapacity", chargingCapacity)
            .put("plugType", plugType)
            .put("vehicleMaxSpeed", vehicleMaxSpeed)
            .put("auxiliaryKwh", auxiliaryKwh)
    }
}