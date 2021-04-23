package com.github.electroway

import org.json.JSONObject

class LoginInfo(val email: String, val password: String) {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("email", email)
            .put("password", password)
    }
}