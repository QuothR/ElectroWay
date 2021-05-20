package com.github.electroway

import org.json.JSONObject

class ChargingPlugInfo(
    val id: Int?,
    val status: Int?,
    val level: Int,
    val connectorType: String,
    val priceKw: Double,
    val chargingSpeedKw: Double
) {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("id", id)
            .put("status", status)
            .put("level", level)
            .put("connectorType", connectorType)
            .put("priceKw", priceKw)
            .put("chargingSpeedKw", chargingSpeedKw)
    }
}