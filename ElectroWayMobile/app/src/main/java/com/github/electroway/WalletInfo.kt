package com.github.electroway

import org.json.JSONObject

class WalletInfo(
    val clientId: String,
    val secret: String
) {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("clientId", clientId)
            .put("secret", secret)
    }
}