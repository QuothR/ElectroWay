package com.github.electroway.models

import org.json.JSONObject

class Wallet(
    val clientId: String,
    val secret: String
) {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("clientId", clientId)
            .put("secret", secret)
    }
}