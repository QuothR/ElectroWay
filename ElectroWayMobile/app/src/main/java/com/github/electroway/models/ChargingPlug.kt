package com.github.electroway.models

import org.json.JSONObject
import java.io.Serializable

class ChargingPlug(
    val id: Int?,
    val connectorType: String,
    val priceKw: Double,
    val chargingSpeedKw: Double
) : Serializable {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("status", 1)
            .put("connectorType", connectorType)
            .put("priceKw", priceKw)
            .put("chargingSpeedKw", chargingSpeedKw)
    }
}