package com.github.electroway.models

import org.json.JSONObject

class Login(val email: String, val password: String) {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("email", email)
            .put("password", password)
    }
}