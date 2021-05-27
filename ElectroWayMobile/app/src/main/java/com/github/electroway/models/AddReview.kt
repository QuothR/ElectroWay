package com.github.electroway.models

import org.json.JSONObject

class AddReview(val rating: Int, val text: String) {
    fun getJson(): JSONObject {
        return JSONObject()
            .put("rating", rating)
            .put("textReview", text)
    }
}