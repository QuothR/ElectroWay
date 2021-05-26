package com.github.electroway

import org.json.JSONArray
import org.json.JSONObject

class RegisterInfo(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val emailAddress: String,
    val address1: String,
    val city: String,
    val country: String,
    val zipcode: String
) {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("username", username)
            .put("password", password)
            .put("firstName", firstName)
            .put("lastName", lastName)
            .put("phoneNumber", phoneNumber)
            .put("emailAddress", emailAddress)
            .put("address1", address1)
            .put("city", city)
            .put("country", country)
            .put("zipcode", zipcode)
    }
}